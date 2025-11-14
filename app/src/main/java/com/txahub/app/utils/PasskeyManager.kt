package com.txahub.app.utils

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.credentials.*
import androidx.credentials.exceptions.*
import com.google.android.gms.fido.Fido
import com.google.android.gms.fido.fido2.Fido2ApiClient
import com.google.android.gms.fido.fido2.api.common.*
import com.txahub.app.data.api.ApiClient
import com.txahub.app.data.models.PasskeyModels
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import java.nio.ByteBuffer
import java.util.UUID

/**
 * Quản lý Passkey operations sử dụng Credential Manager API và Fido2ApiClient
 */
class PasskeyManager(private val context: Context) {
    
    private val credentialManager = CredentialManager.create(context)
    private val fido2ApiClient: Fido2ApiClient? = try {
        Fido.getFido2ApiClient(context)
    } catch (e: Exception) {
        Log.e("PasskeyManager", "Fido2ApiClient not available", e)
        null
    }
    
    companion object {
        private const val TAG = "PasskeyManager"
        // Relying Party ID từ server (ví dụ: txahub.click)
        private const val RP_ID = "txahub.click"
    }
    
    /**
     * Tạo challenge từ server và đăng ký passkey
     */
    suspend fun registerPasskey(
        userName: String,
        userDisplayName: String,
        userIcon: String? = null
    ): Result<PasskeyModels.VerifyRegistrationResponse> {
        return try {
            // 1. Tạo challenge từ server
            val challengeResponse = ApiClient.passkeyApi.createChallenge(
                PasskeyModels.CreateChallengeRequest("registration")
            )
            
            if (!challengeResponse.isSuccessful || challengeResponse.body() == null) {
                return Result.failure(Exception("Failed to create challenge: ${challengeResponse.message()}"))
            }
            
            val challengeData = challengeResponse.body()!!
            
            // 2. Tạo PublicKeyCredentialCreationOptions từ server response
            val publicKeyCredentialCreationOptions = createPublicKeyCredentialCreationOptions(
                challengeData,
                userName,
                userDisplayName,
                userIcon
            )
            
            // 3. Đăng ký passkey với Fido2ApiClient
            val result = fido2ApiClient?.register(publicKeyCredentialCreationOptions)?.await()
                ?: return Result.failure(Exception("Fido2ApiClient not available"))
            
            // 4. Chuyển đổi kết quả thành JSON string
            val credentialJson = convertRegistrationResultToJson(result)
            
            // 5. Gửi credential lên server để verify
            val verifyResponse = ApiClient.passkeyApi.verifyRegistration(
                PasskeyModels.VerifyRegistrationRequest(
                    challengeId = challengeData.challengeId,
                    credential = credentialJson,
                    name = null
                )
            )
            
            if (!verifyResponse.isSuccessful || verifyResponse.body() == null) {
                return Result.failure(Exception("Failed to verify registration: ${verifyResponse.message()}"))
            }
            
            Result.success(verifyResponse.body()!!)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error registering passkey", e)
            Result.failure(e)
        }
    }
    
    /**
     * Tạo challenge từ server và xác thực passkey để đăng nhập
     */
    suspend fun authenticatePasskey(): Result<PasskeyModels.VerifyAuthenticationResponse> {
        return try {
            // 1. Tạo challenge từ server
            val challengeResponse = ApiClient.passkeyApi.createChallenge(
                PasskeyModels.CreateChallengeRequest("authentication")
            )
            
            if (!challengeResponse.isSuccessful || challengeResponse.body() == null) {
                return Result.failure(Exception("Failed to create challenge: ${challengeResponse.message()}"))
            }
            
            val challengeData = challengeResponse.body()!!
            
            // 2. Tạo PublicKeyCredentialRequestOptions từ server response
            val publicKeyCredentialRequestOptions = createPublicKeyCredentialRequestOptions(
                challengeData
            )
            
            // 3. Xác thực passkey với Fido2ApiClient
            val result = fido2ApiClient?.sign(publicKeyCredentialRequestOptions)?.await()
                ?: return Result.failure(Exception("Fido2ApiClient not available"))
            
            // 4. Chuyển đổi kết quả thành JSON string
            val credentialJson = convertAuthenticationResultToJson(result)
            
            // 5. Gửi credential lên server để verify
            val verifyResponse = ApiClient.passkeyApi.verifyAuthentication(
                PasskeyModels.VerifyAuthenticationRequest(
                    challengeId = challengeData.challengeId,
                    credential = credentialJson
                )
            )
            
            if (!verifyResponse.isSuccessful || verifyResponse.body() == null) {
                return Result.failure(Exception("Failed to verify authentication: ${verifyResponse.message()}"))
            }
            
            Result.success(verifyResponse.body()!!)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error authenticating passkey", e)
            Result.failure(e)
        }
    }
    
    /**
     * Tạo PublicKeyCredentialCreationOptions từ server response
     */
    private fun createPublicKeyCredentialCreationOptions(
        challengeData: PasskeyModels.CreateChallengeResponse,
        userName: String,
        userDisplayName: String,
        userIcon: String?
    ): PublicKeyCredentialCreationOptions {
        val rp = challengeData.rp
        val user = challengeData.user ?: throw Exception("User info not found in challenge")
        
        // Convert challenge từ base64 string sang byte array
        val challengeBytes = Base64.decode(challengeData.challenge, Base64.URL_SAFE or Base64.NO_WRAP)
        
        // Convert user ID từ base64 string sang byte array
        val userIdBytes = Base64.decode(user.id, Base64.URL_SAFE or Base64.NO_WRAP)
        
        // Tạo PublicKeyCredentialRpEntity
        val rpEntity = PublicKeyCredentialRpEntity(
            rp.id,
            rp.name,
            rp.icon?.let { android.net.Uri.parse(it) }
        )
        
        // Tạo PublicKeyCredentialUserEntity
        val userEntity = PublicKeyCredentialUserEntity(
            userIdBytes,
            userName,
            userDisplayName,
            userIcon?.let { android.net.Uri.parse(it) }
        )
        
        // Tạo PublicKeyCredentialParameters
        val parameters = challengeData.pubKeyCredParams.map {
            PublicKeyCredentialParameters(
                PublicKeyCredentialType.PUBLIC_KEY.toString(),
                it.alg
            )
        }
        
        // Tạo AuthenticatorSelectionCriteria
        val authenticatorSelection = AuthenticatorSelectionCriteria.Builder()
            .setAttachment(AuthenticatorAttachment.PLATFORM_OR_CROSS_PLATFORM)
            .setRequireResidentKey(
                when (challengeData.authenticatorSelection.residentKey) {
                    "required" -> true
                    "preferred" -> true
                    else -> false
                }
            )
            .setUserVerificationRequirement(
                when (challengeData.authenticatorSelection.userVerification) {
                    "required" -> UserVerificationRequirement.REQUIRED
                    "preferred" -> UserVerificationRequirement.PREFERRED
                    "discouraged" -> UserVerificationRequirement.DISCOURAGED
                    else -> UserVerificationRequirement.PREFERRED
                }
            )
            .build()
        
        // Tạo PublicKeyCredentialCreationOptions
        return PublicKeyCredentialCreationOptions.Builder()
            .setRp(rpEntity)
            .setUser(userEntity)
            .setChallenge(challengeBytes)
            .setParameters(parameters)
            .setTimeoutSeconds(challengeData.timeout / 1000.0)
            .setAuthenticatorSelection(authenticatorSelection)
            .build()
    }
    
    /**
     * Tạo PublicKeyCredentialRequestOptions từ server response
     */
    private fun createPublicKeyCredentialRequestOptions(
        challengeData: PasskeyModels.CreateChallengeResponse
    ): PublicKeyCredentialRequestOptions {
        // Convert challenge từ base64 string sang byte array
        val challengeBytes = Base64.decode(challengeData.challenge, Base64.URL_SAFE or Base64.NO_WRAP)
        
        // Tạo PublicKeyCredentialRequestOptions
        return PublicKeyCredentialRequestOptions.Builder()
            .setRpId(challengeData.rp.id)
            .setChallenge(challengeBytes)
            .setTimeoutSeconds(challengeData.timeout / 1000.0)
            .setUserVerificationRequirement(
                when (challengeData.userVerification) {
                    "required" -> UserVerificationRequirement.REQUIRED
                    "preferred" -> UserVerificationRequirement.PREFERRED
                    "discouraged" -> UserVerificationRequirement.DISCOURAGED
                    else -> UserVerificationRequirement.PREFERRED
                }
            )
            .build()
    }
    
    /**
     * Chuyển đổi RegistrationResult thành JSON string
     */
    private fun convertRegistrationResultToJson(result: AuthenticatorAttestationResponse): String {
        val json = JSONObject()
        
        // ID (base64url encoded)
        val id = Base64.encodeToString(result.getKeyId(), Base64.URL_SAFE or Base64.NO_WRAP)
        json.put("id", id)
        
        // Response
        val response = JSONObject()
        response.put("clientDataJSON", Base64.encodeToString(result.getClientDataJSON(), Base64.URL_SAFE or Base64.NO_WRAP))
        response.put("attestationObject", Base64.encodeToString(result.getAttestationObject(), Base64.URL_SAFE or Base64.NO_WRAP))
        json.put("response", response)
        
        // Type
        json.put("type", "public-key")
        
        return json.toString()
    }
    
    /**
     * Chuyển đổi AuthenticationResult thành JSON string
     */
    private fun convertAuthenticationResultToJson(result: AuthenticatorAssertionResponse): String {
        val json = JSONObject()
        
        // ID (base64url encoded)
        val id = Base64.encodeToString(result.getKeyId(), Base64.URL_SAFE or Base64.NO_WRAP)
        json.put("id", id)
        
        // Response
        val response = JSONObject()
        response.put("clientDataJSON", Base64.encodeToString(result.getClientDataJSON(), Base64.URL_SAFE or Base64.NO_WRAP))
        response.put("authenticatorData", Base64.encodeToString(result.getAuthenticatorData(), Base64.URL_SAFE or Base64.NO_WRAP))
        response.put("signature", Base64.encodeToString(result.getSignature(), Base64.URL_SAFE or Base64.NO_WRAP))
        result.getUserHandle()?.let {
            response.put("userHandle", Base64.encodeToString(it, Base64.URL_SAFE or Base64.NO_WRAP))
        }
        json.put("response", response)
        
        // Type
        json.put("type", "public-key")
        
        return json.toString()
    }
    
    /**
     * Kiểm tra xem thiết bị có hỗ trợ passkey không
     */
    fun isPasskeySupported(): Boolean {
        return fido2ApiClient != null
    }
}

