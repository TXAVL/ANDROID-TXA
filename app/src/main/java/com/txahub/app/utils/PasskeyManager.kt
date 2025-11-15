package com.txahub.app.utils

import android.app.Activity
import android.content.Context
import android.util.Base64
import androidx.credentials.*
import androidx.credentials.exceptions.*
import kotlinx.coroutines.*
import org.json.JSONObject
import android.util.Log

/**
 * PasskeyManager - Quản lý tất cả các thao tác Passkey (WebAuthn/FIDO2)
 * 
 * Tính năng này sử dụng Credential Manager API và FIDO2 API của Google Play Services
 * để hỗ trợ xác thực không mật khẩu.
 */
class PasskeyManager(private val context: Context) {
    
    private val logWriter = LogWriter(context)
    private val logSettings = LogSettingsManager(context)
    private val credentialManager = CredentialManager.create(context)
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    companion object {
        private const val TAG = "PasskeyManager"
        private const val DEFAULT_TIMEOUT = 60000L // 60 seconds
        private const val DEFAULT_RP_ID = "txahub.click"
        private const val DEFAULT_ORIGIN = "https://txahub.click"
    }
    
    /**
     * Kiểm tra xem thiết bị có hỗ trợ Passkey không
     */
    fun isPasskeySupported(): Boolean {
        return try {
            CredentialManager.create(context)
            logPasskey("DEBUG", "Passkey is supported on this device")
            true
        } catch (e: Exception) {
            logPasskey("ERROR", "Passkey is not supported: ${e.message}")
            false
        }
    }
    
    /**
     * Tạo Passkey mới (Registration)
     * 
     * @param configJson JSON config từ WebView/JavaScript
     * @param callback JavaScript callback function name
     */
    fun createPasskey(configJson: String, callback: String, activity: android.app.Activity) {
        coroutineScope.launch {
            try {
                logPasskey("DEBUG", "=== Creating Passkey ===")
                logPasskey("DEBUG", "Config: $configJson")
                
                val config = JSONObject(configJson)
                
                // Parse và validate config
                val rpId = config.optString("rpId") ?: config.optJSONObject("rp")?.optString("id") ?: DEFAULT_RP_ID
                val rpName = config.optJSONObject("rp")?.optString("name") ?: "TXA Hub"
                val origin = convertOrigin(config.optString("origin", ""))
                val hostname = config.optString("hostname", rpId)
                
                logPasskey("DEBUG", "RP ID: $rpId")
                logPasskey("DEBUG", "Origin: ${config.optString("origin", "")}")
                logPasskey("DEBUG", "Hostname: $hostname")
                logPasskey("DEBUG", "Credential Origin: $origin")
                
                // Validate RP ID
                if (hostname.isNotEmpty() && !rpId.contains(hostname) && rpId != hostname) {
                    logPasskey("WARN", "RP ID ($rpId) does not match hostname ($hostname)")
                }
                
                // Parse challenge
                val challengeBase64 = config.getString("challenge")
                val challenge = Base64.decode(challengeBase64, Base64.URL_SAFE or Base64.NO_WRAP)
                
                // Build requestJson từ config (Credential Manager cần JSON string)
                // Các thông tin user sẽ được parse trong buildRequestJson
                val requestJson = buildRequestJson(config, challenge, rpId, rpName, origin)
                
                // Create request với requestJson
                val createRequest = try {
                    // Thử constructor mới với origin
                    val constructor = CreatePublicKeyCredentialRequest::class.java.getConstructor(
                        String::class.java,
                        Boolean::class.java,
                        String::class.java
                    )
                    constructor.newInstance(requestJson, true, origin) as CreatePublicKeyCredentialRequest
                } catch (e: Exception) {
                    try {
                        // Thử constructor với requestJson và preferImmediatelyAvailableCredentials
                        val constructor = CreatePublicKeyCredentialRequest::class.java.getConstructor(
                            String::class.java,
                            Boolean::class.java
                        )
                        constructor.newInstance(requestJson, true) as CreatePublicKeyCredentialRequest
                    } catch (e2: Exception) {
                        logPasskey("WARN", "Using fallback constructor: ${e2.message}")
                        // Fallback về constructor cơ bản
                        CreatePublicKeyCredentialRequest(requestJson)
                    }
                }
                
                // Gọi Credential Manager
                val result = credentialManager.createCredential(
                    activity,
                    createRequest
                )
                
                // Lấy credential từ result bằng reflection
                val credential = getCredentialFromCreateResult(result)
                
                if (credential is PublicKeyCredential) {
                    val responseJson = credential.authenticationResponseJson
                    logPasskey("INFO", "Passkey created successfully")
                    
                    // Gửi response về callback
                    sendResponseToWebView(callback, true, JSONObject(responseJson))
                } else {
                    throw Exception("Unexpected credential type: ${credential?.javaClass?.name}")
                }
                
            } catch (e: CreateCredentialCancellationException) {
                logPasskey("ERROR", "User cancelled: ${e.message}")
                sendErrorToWebView(callback, "NotAllowedError", "User cancelled")
            } catch (e: CreateCredentialInterruptedException) {
                logPasskey("ERROR", "Interrupted: ${e.message}")
                sendErrorToWebView(callback, "NotAllowedError", "Operation interrupted")
            } catch (e: CreateCredentialProviderConfigurationException) {
                logPasskey("ERROR", "Provider configuration error: ${e.message}")
                sendErrorToWebView(callback, "NotSupportedError", "Provider configuration error: ${e.message}")
            } catch (e: CreateCredentialUnsupportedException) {
                logPasskey("ERROR", "Unsupported: ${e.message}")
                sendErrorToWebView(callback, "NotSupportedError", "Unsupported: ${e.message}")
            } catch (e: CreateCredentialUnknownException) {
                logPasskey("ERROR", "Unknown error: ${e.message}")
                sendErrorToWebView(callback, "UnknownError", "Unknown error: ${e.message}")
            } catch (e: Exception) {
                logPasskey("ERROR", "Error creating passkey: ${e.message}")
                e.printStackTrace()
                sendErrorToWebView(callback, "UnknownError", "Error creating passkey: ${e.message}")
            }
        }
    }
    
    /**
     * Lấy Passkey (Authentication)
     * 
     * @param configJson JSON config từ WebView/JavaScript
     * @param callback JavaScript callback function name
     */
    fun getPasskey(configJson: String, callback: String, activity: android.app.Activity) {
        coroutineScope.launch {
            try {
                logPasskey("DEBUG", "=== Getting Passkey ===")
                logPasskey("DEBUG", "Config: $configJson")
                
                val config = JSONObject(configJson)
                
                // Parse và validate config
                val rpId = config.getString("rpId") ?: DEFAULT_RP_ID
                val origin = convertOrigin(config.optString("origin", ""))
                val hostname = config.optString("hostname", rpId)
                
                logPasskey("DEBUG", "RP ID: $rpId")
                logPasskey("DEBUG", "Origin: ${config.optString("origin", "")}")
                logPasskey("DEBUG", "Hostname: $hostname")
                logPasskey("DEBUG", "Credential Origin: $origin")
                
                // Parse challenge
                val challengeBase64 = config.getString("challenge")
                val challenge = Base64.decode(challengeBase64, Base64.URL_SAFE or Base64.NO_WRAP)
                
                // Build requestJson từ config (Credential Manager cần JSON string)
                val requestJson = buildGetRequestJson(config, challenge, rpId, origin)
                
                // Create request với requestJson
                val getRequest = try {
                    // Thử constructor mới với origin
                    val constructor = GetPublicKeyCredentialOption::class.java.getConstructor(
                        String::class.java,
                        Boolean::class.java,
                        String::class.java
                    )
                    val option = constructor.newInstance(requestJson, true, origin) as GetPublicKeyCredentialOption
                    GetCredentialRequest.Builder()
                        .addCredentialOption(option)
                        .build()
                } catch (e: Exception) {
                    try {
                        // Thử constructor với requestJson và preferImmediatelyAvailableCredentials
                        val constructor = GetPublicKeyCredentialOption::class.java.getConstructor(
                            String::class.java,
                            Boolean::class.java
                        )
                        val option = constructor.newInstance(requestJson, true) as GetPublicKeyCredentialOption
                        GetCredentialRequest.Builder()
                            .addCredentialOption(option)
                            .build()
                    } catch (e2: Exception) {
                        logPasskey("WARN", "Using fallback constructor: ${e2.message}")
                        // Fallback về constructor cơ bản
                        val option = GetPublicKeyCredentialOption(requestJson)
                        GetCredentialRequest.Builder()
                            .addCredentialOption(option)
                            .build()
                    }
                }
                
                // Gọi Credential Manager
                val result = credentialManager.getCredential(
                    activity,
                    getRequest
                )
                
                // Lấy credential từ result bằng reflection
                val credential = getCredentialFromGetResult(result)
                
                if (credential is PublicKeyCredential) {
                    val responseJson = credential.authenticationResponseJson
                    logPasskey("INFO", "Passkey retrieved successfully")
                    
                    // Gửi response về callback
                    sendResponseToWebView(callback, true, JSONObject(responseJson))
                } else {
                    throw Exception("Unexpected credential type: ${credential?.javaClass?.name}")
                }
                
            } catch (e: GetCredentialCancellationException) {
                logPasskey("ERROR", "User cancelled: ${e.message}")
                sendErrorToWebView(callback, "NotAllowedError", "User cancelled")
            } catch (e: GetCredentialInterruptedException) {
                logPasskey("ERROR", "Interrupted: ${e.message}")
                sendErrorToWebView(callback, "NotAllowedError", "Operation interrupted")
            } catch (e: GetCredentialProviderConfigurationException) {
                logPasskey("ERROR", "Provider configuration error: ${e.message}")
                sendErrorToWebView(callback, "NotSupportedError", "Provider configuration error: ${e.message}")
            } catch (e: GetCredentialUnsupportedException) {
                logPasskey("ERROR", "Unsupported: ${e.message}")
                sendErrorToWebView(callback, "NotSupportedError", "Unsupported: ${e.message}")
            } catch (e: GetCredentialUnknownException) {
                logPasskey("ERROR", "Unknown error: ${e.message}")
                sendErrorToWebView(callback, "UnknownError", "Unknown error: ${e.message}")
            } catch (e: Exception) {
                logPasskey("ERROR", "Error getting passkey: ${e.message}")
                e.printStackTrace()
                sendErrorToWebView(callback, "UnknownError", "Error getting passkey: ${e.message}")
            }
        }
    }
    
    /**
     * Convert origin từ custom scheme sang HTTPS
     */
    private fun convertOrigin(origin: String): String {
        return when {
            origin.startsWith("txahub://") -> DEFAULT_ORIGIN
            origin.isNotEmpty() -> origin
            else -> DEFAULT_ORIGIN
        }
    }
    
    /**
     * Build requestJson cho createPasskey từ config
     */
    private fun buildRequestJson(
        config: JSONObject,
        challenge: ByteArray,
        rpId: String,
        rpName: String,
        @Suppress("UNUSED_PARAMETER") origin: String
    ): String {
        val requestJson = JSONObject()
        
        // Challenge (Base64 URL-safe)
        requestJson.put("challenge", Base64.encodeToString(challenge, Base64.URL_SAFE or Base64.NO_WRAP))
        
        // RP
        val rp = JSONObject()
        rp.put("id", rpId)
        rp.put("name", rpName)
        requestJson.put("rp", rp)
        
        // User
        val user = config.getJSONObject("user")
        val userJson = JSONObject()
        userJson.put("id", user.getString("id"))
        userJson.put("name", user.getString("name"))
        userJson.put("displayName", user.optString("displayName", user.getString("name")))
        if (user.has("icon") && !user.isNull("icon")) {
            userJson.put("icon", user.getString("icon"))
        }
        requestJson.put("user", userJson)
        
        // pubKeyCredParams
        requestJson.put("pubKeyCredParams", config.getJSONArray("pubKeyCredParams"))
        
        // timeout
        requestJson.put("timeout", config.optLong("timeout", DEFAULT_TIMEOUT))
        
        // attestation
        requestJson.put("attestation", config.optString("attestation", "none"))
        
        // authenticatorSelection
        if (config.has("authenticatorSelection")) {
            requestJson.put("authenticatorSelection", config.getJSONObject("authenticatorSelection"))
        } else {
            val authenticatorSelection = JSONObject()
            authenticatorSelection.put("authenticatorAttachment", "platform")
            authenticatorSelection.put("userVerification", config.optString("userVerification", "preferred"))
            authenticatorSelection.put("residentKey", "required")
            requestJson.put("authenticatorSelection", authenticatorSelection)
        }
        
        return requestJson.toString()
    }
    
    /**
     * Build requestJson cho getPasskey từ config
     */
    private fun buildGetRequestJson(
        config: JSONObject,
        challenge: ByteArray,
        rpId: String,
        @Suppress("UNUSED_PARAMETER") origin: String
    ): String {
        val requestJson = JSONObject()
        
        // Challenge (Base64 URL-safe)
        requestJson.put("challenge", Base64.encodeToString(challenge, Base64.URL_SAFE or Base64.NO_WRAP))
        
        // rpId
        requestJson.put("rpId", rpId)
        
        // timeout
        requestJson.put("timeout", config.optLong("timeout", DEFAULT_TIMEOUT))
        
        // allowCredentials (optional)
        if (config.has("allowCredentials")) {
            requestJson.put("allowCredentials", config.getJSONArray("allowCredentials"))
        }
        
        // userVerification
        requestJson.put("userVerification", config.optString("userVerification", "preferred"))
        
        return requestJson.toString()
    }
    
    /**
     * Lấy credential từ CreateCredentialResponse bằng reflection
     */
    private fun getCredentialFromCreateResult(result: CreateCredentialResponse): Credential? {
        return try {
            val credentialField = result.javaClass.getDeclaredField("credential")
            credentialField.isAccessible = true
            credentialField.get(result) as? Credential
        } catch (e: Exception) {
            logPasskey("ERROR", "Cannot access credential from CreateCredentialResponse: ${e.message}")
            null
        }
    }
    
    /**
     * Lấy credential từ GetCredentialResponse bằng reflection
     */
    private fun getCredentialFromGetResult(result: GetCredentialResponse): Credential? {
        return try {
            val credentialField = result.javaClass.getDeclaredField("credential")
            credentialField.isAccessible = true
            credentialField.get(result) as? Credential
        } catch (e: Exception) {
            logPasskey("ERROR", "Cannot access credential from GetCredentialResponse: ${e.message}")
            null
        }
    }
    
    // Callback interface cho Passkey operations
    interface PasskeyCallback {
        fun onSuccess(data: JSONObject)
        fun onError(code: String, message: String)
    }
    
    private var passkeyCallback: PasskeyCallback? = null
    
    /**
     * Set callback cho Passkey operations
     */
    fun setCallback(callback: PasskeyCallback) {
        this.passkeyCallback = callback
    }
    
    /**
     * Gửi response thành công về callback
     */
    private fun sendResponseToWebView(
        @Suppress("UNUSED_PARAMETER") callback: String,
        success: Boolean,
        data: JSONObject
    ) {
        logPasskey("DEBUG", "Response: success=$success, data=${data.toString()}")
        passkeyCallback?.onSuccess(data)
    }
    
    /**
     * Gửi error về callback
     */
    private fun sendErrorToWebView(
        @Suppress("UNUSED_PARAMETER") callback: String,
        code: String,
        message: String
    ) {
        logPasskey("DEBUG", "Error: code=$code, message=$message")
        passkeyCallback?.onError(code, message)
    }
    
    /**
     * Log Passkey operations
     * Luôn log vào file sau khi cấp quyền ghi file, bất kể setting
     */
    private fun logPasskey(level: String, message: String) {
        // Luôn log vào logcat
        when (level) {
            "DEBUG" -> Log.d(TAG, message)
            "INFO" -> Log.i(TAG, message)
            "WARN" -> Log.w(TAG, message)
            "ERROR" -> Log.e(TAG, message)
        }
        
        // Luôn ghi vào file nếu có quyền (không kiểm tra setting)
        logWriter.writePasskeyLog(message, level)
    }
    
    /**
     * Cleanup resources
     */
    fun cleanup() {
        coroutineScope.cancel()
    }
}
