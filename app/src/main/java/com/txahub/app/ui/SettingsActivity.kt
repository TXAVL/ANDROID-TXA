package com.txahub.app.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.txahub.app.R
import com.txahub.app.databinding.ActivitySettingsBinding
import com.txahub.app.data.local.PreferencesManager
import com.txahub.app.utils.LocaleHelper
import com.txahub.app.utils.UpdateChecker
import com.txahub.app.utils.LogSettingsManager
import com.txahub.app.utils.NotificationTTSManager
import com.txahub.app.utils.PasskeyManager
import com.txahub.app.data.api.ApiClient
import com.txahub.app.data.models.PasskeyModels
import org.json.JSONObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var logSettingsManager: LogSettingsManager
    private lateinit var updateChecker: UpdateChecker
    private lateinit var ttsManager: NotificationTTSManager
    private lateinit var passkeyManager: PasskeyManager
    private var currentLanguage: String = LocaleHelper.LANGUAGE_AUTO
    private var selectedLanguage: String = LocaleHelper.LANGUAGE_AUTO
    private var currentChallengeId: String? = null
    
    override fun attachBaseContext(newBase: Context) {
        val preferencesManager = PreferencesManager(newBase)
        val language = try {
            runBlocking {
                preferencesManager.getLanguageSync()
            }
        } catch (e: Exception) {
            LocaleHelper.LANGUAGE_AUTO
        }
        super.attachBaseContext(LocaleHelper.setLocale(newBase, language))
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        preferencesManager = PreferencesManager(this)
        logSettingsManager = LogSettingsManager(this)
        updateChecker = UpdateChecker(this)
        ttsManager = NotificationTTSManager(this)
        passkeyManager = PasskeyManager(this)
        
        // Setup Passkey callback
        passkeyManager.setCallback(object : PasskeyManager.PasskeyCallback {
            override fun onSuccess(data: JSONObject) {
                onPasskeyCreated(true, data)
            }
            
            override fun onError(code: String, message: String) {
                runOnUiThread {
                    binding.btnRegisterPasskey.isEnabled = true
                    Toast.makeText(
                        this@SettingsActivity,
                        "Lỗi Passkey: $message",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.txa_global_settings)
        
        setupUI()
        loadCurrentLanguage()
        loadSystemInfo()
        loadLogSettings()
        loadTTSSettings()
        
        // Xử lý scroll đến phần update nếu có deeplink
        handleDeepLink(intent)
    }
    
    private fun handleDeepLink(intent: Intent?) {
        val scrollTo = intent?.getStringExtra("scroll_to")
        if (scrollTo == "update") {
            // Scroll đến phần update sau khi layout đã render
            binding.root.post {
                binding.nestedScrollView.post {
                    binding.btnCheckUpdate.requestFocus()
                    binding.nestedScrollView.smoothScrollTo(0, binding.btnCheckUpdate.top)
                }
            }
        }
    }
    
    private fun setupUI() {
        binding.tvLanguageTitle.text = getString(R.string.txa_global_language)
        
        // Setup radio buttons
        binding.rbAuto.text = getString(R.string.txa_global_language_auto)
        binding.rbVietnamese.text = getString(R.string.txa_global_language_vi)
        binding.rbEnglish.text = getString(R.string.txa_global_language_en)
        
        binding.rbAuto.setOnClickListener { selectedLanguage = LocaleHelper.LANGUAGE_AUTO }
        binding.rbVietnamese.setOnClickListener { selectedLanguage = LocaleHelper.LANGUAGE_VI }
        binding.rbEnglish.setOnClickListener { selectedLanguage = LocaleHelper.LANGUAGE_EN }
        
        binding.btnUpdate.setOnClickListener {
            updateLanguage()
        }
        
        // Setup check update button
        binding.btnCheckUpdate.setOnClickListener {
            checkUpdate()
        }
        
        // Setup log switches
        binding.switchLogApi.setOnCheckedChangeListener { _, isChecked ->
            logSettingsManager.setApiLogEnabled(isChecked)
            Toast.makeText(this, 
                getString(R.string.txa_global_log_status, 
                    getString(R.string.txa_global_log_enable_api),
                    if (isChecked) getString(R.string.txa_global_success) else getString(R.string.txa_global_cancel)
                ),
                Toast.LENGTH_SHORT).show()
        }
        
        binding.switchLogApp.setOnCheckedChangeListener { _, isChecked ->
            logSettingsManager.setAppLogEnabled(isChecked)
            Toast.makeText(this, 
                getString(R.string.txa_global_log_status,
                    getString(R.string.txa_global_log_enable_app),
                    if (isChecked) getString(R.string.txa_global_success) else getString(R.string.txa_global_cancel)
                ),
                Toast.LENGTH_SHORT).show()
        }
        
        binding.switchLogCrash.setOnCheckedChangeListener { _, isChecked ->
            logSettingsManager.setCrashLogEnabled(isChecked)
            Toast.makeText(this, 
                getString(R.string.txa_global_log_status,
                    getString(R.string.txa_global_log_enable_crash),
                    if (isChecked) getString(R.string.txa_global_success) else getString(R.string.txa_global_cancel)
                ),
                Toast.LENGTH_SHORT).show()
        }
        
        binding.switchLogUpdate.setOnCheckedChangeListener { _, isChecked ->
            logSettingsManager.setUpdateCheckLogEnabled(isChecked)
            Toast.makeText(this, 
                getString(R.string.txa_global_log_status,
                    getString(R.string.txa_global_log_enable_update),
                    if (isChecked) getString(R.string.txa_global_success) else getString(R.string.txa_global_cancel)
                ),
                Toast.LENGTH_SHORT).show()
        }
        
        binding.switchLogPasskey.setOnCheckedChangeListener { _, isChecked ->
            logSettingsManager.setPasskeyLogEnabled(isChecked)
            Toast.makeText(this, 
                getString(R.string.txa_global_log_status,
                    getString(R.string.txa_global_log_enable_passkey),
                    if (isChecked) getString(R.string.txa_global_success) else getString(R.string.txa_global_cancel)
                ),
                Toast.LENGTH_SHORT).show()
        }
        
        // Setup TTS switch
        binding.switchTTS.setOnCheckedChangeListener { _, isChecked ->
            ttsManager.setTTSEnabled(isChecked)
            Toast.makeText(this, 
                getString(R.string.txa_global_log_status,
                    getString(R.string.txa_global_tts_enable),
                    if (isChecked) getString(R.string.txa_global_success) else getString(R.string.txa_global_cancel)
                ),
                Toast.LENGTH_SHORT).show()
        }
        
        // Setup Passkey register button
        binding.btnRegisterPasskey.setOnClickListener {
            registerPasskey()
        }
    }
    
    private fun loadCurrentLanguage() {
        lifecycleScope.launch {
            currentLanguage = preferencesManager.getLanguageSync()
            selectedLanguage = currentLanguage
            
            when (currentLanguage) {
                LocaleHelper.LANGUAGE_AUTO -> binding.rbAuto.isChecked = true
                LocaleHelper.LANGUAGE_VI -> binding.rbVietnamese.isChecked = true
                LocaleHelper.LANGUAGE_EN -> binding.rbEnglish.isChecked = true
            }
        }
    }
    
    private fun loadSystemInfo() {
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val versionName = packageInfo.versionName ?: "1.0.0_txa"
            val versionCode = packageInfo.longVersionCode.toInt()
            
            binding.tvAppVersion.text = getString(R.string.txa_global_app_version, versionName)
            binding.tvVersionCode.text = getString(R.string.txa_global_version_code, versionCode.toString())
            binding.tvAboutApp.text = getString(R.string.txa_global_about_app)
        } catch (e: PackageManager.NameNotFoundException) {
            binding.tvAppVersion.text = getString(R.string.txa_global_app_version, "1.0.0_txa")
            binding.tvVersionCode.text = getString(R.string.txa_global_version_code, "1")
        }
    }
    
    private fun loadLogSettings() {
        binding.switchLogApi.isChecked = logSettingsManager.isApiLogEnabled()
        binding.switchLogApp.isChecked = logSettingsManager.isAppLogEnabled()
        binding.switchLogCrash.isChecked = logSettingsManager.isCrashLogEnabled()
        binding.switchLogUpdate.isChecked = logSettingsManager.isUpdateCheckLogEnabled()
        binding.switchLogPasskey.isChecked = logSettingsManager.isPasskeyLogEnabled()
    }
    
    private fun loadTTSSettings() {
        binding.switchTTS.isChecked = ttsManager.isTTSEnabled()
    }
    
    
    private fun checkUpdate() {
        binding.btnCheckUpdate.isEnabled = false
        binding.btnCheckUpdate.text = getString(R.string.txa_global_checking_update)
        
        updateChecker.checkUpdate { updateInfo ->
            runOnUiThread {
                binding.btnCheckUpdate.isEnabled = true
                binding.btnCheckUpdate.text = getString(R.string.txa_global_check_update)
                
                if (updateInfo != null) {
                    // Có bản cập nhật
                    AlertDialog.Builder(this)
                        .setTitle(getString(R.string.txa_global_update_available, updateInfo.versionName))
                        .setMessage(getString(R.string.txa_global_update_available, updateInfo.versionName))
                        .setPositiveButton(getString(R.string.txa_global_ok)) { _, _ ->
                            // Mở trình duyệt để tải
                            val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(updateInfo.downloadUrl))
                            startActivity(intent)
                        }
                        .setNegativeButton(getString(R.string.txa_global_cancel), null)
                        .show()
                } else {
                    // Không có bản cập nhật
                    Toast.makeText(this, getString(R.string.txa_global_no_update), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun updateLanguage() {
        if (selectedLanguage == currentLanguage) {
            Toast.makeText(this, getString(R.string.txa_global_language_changed), Toast.LENGTH_SHORT).show()
            return
        }
        
        lifecycleScope.launch {
            preferencesManager.saveLanguage(selectedLanguage)
            currentLanguage = selectedLanguage
            recreate()
        }
    }
    
    private fun registerPasskey() {
        binding.btnRegisterPasskey.isEnabled = false
        
        lifecycleScope.launch {
            try {
                // Gọi API để lấy challenge cho registration
                val response = ApiClient.passkeyApi.createChallenge(
                    PasskeyModels.CreateChallengeRequest("registration")
                )
                
                if (response.isSuccessful && response.body()?.success == true) {
                    val challengeData = response.body()!!.data!!
                    
                    // Lưu challenge_id để dùng khi verify
                    currentChallengeId = challengeData.challengeId
                    
                    // Build config JSON cho createPasskey
                    val config = JSONObject().apply {
                        put("challenge", challengeData.challenge)
                        put("rpId", challengeData.rp.id)
                        put("rp", JSONObject().apply {
                            put("id", challengeData.rp.id)
                            put("name", challengeData.rp.name)
                            challengeData.rp.icon?.let { put("icon", it) }
                        })
                        put("timeout", challengeData.timeout)
                        put("userVerification", challengeData.userVerification)
                        put("attestation", challengeData.attestation)
                        put("origin", "https://txahub.click")
                        put("hostname", challengeData.rp.id)
                        
                        // User info (chỉ có khi type = "registration")
                        challengeData.user?.let { user ->
                            put("user", JSONObject().apply {
                                put("id", user.id)
                                put("name", user.name)
                                put("displayName", user.displayName ?: user.name)
                                user.icon?.let { put("icon", it) }
                            })
                        }
                        
                        // pubKeyCredParams
                        val pubKeyCredParams = org.json.JSONArray()
                        challengeData.pubKeyCredParams.forEach { param ->
                            pubKeyCredParams.put(JSONObject().apply {
                                put("type", param.type)
                                put("alg", param.alg)
                            })
                        }
                        put("pubKeyCredParams", pubKeyCredParams)
                        
                        // authenticatorSelection
                        put("authenticatorSelection", JSONObject().apply {
                            put("authenticatorAttachment", challengeData.authenticatorSelection.authenticatorAttachment)
                            put("userVerification", challengeData.authenticatorSelection.userVerification)
                            put("residentKey", challengeData.authenticatorSelection.residentKey)
                        })
                    }
                    
                    // Gọi PasskeyManager để tạo Passkey
                    passkeyManager.createPasskey(config.toString(), "callback", this@SettingsActivity)
                } else {
                    val errorMessage = response.body()?.message ?: "Không thể tạo challenge"
                    Toast.makeText(
                        this@SettingsActivity,
                        errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                    binding.btnRegisterPasskey.isEnabled = true
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@SettingsActivity,
                    "Lỗi: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                binding.btnRegisterPasskey.isEnabled = true
            }
        }
    }
    
    // Callback từ PasskeyManager (sẽ được gọi từ WebView hoặc trực tiếp)
    fun onPasskeyCreated(success: Boolean, responseJson: JSONObject?) {
        runOnUiThread {
            binding.btnRegisterPasskey.isEnabled = true
            
            if (success && responseJson != null && currentChallengeId != null) {
                // Gọi API verify_registration
                lifecycleScope.launch {
                    try {
                        val verifyResponse = ApiClient.passkeyApi.verifyRegistration(
                            PasskeyModels.VerifyRegistrationRequest(
                                challengeId = currentChallengeId!!,
                                credential = responseJson.toString()
                            )
                        )
                        
                        if (verifyResponse.isSuccessful && verifyResponse.body()?.success == true) {
                            Toast.makeText(
                                this@SettingsActivity,
                                "Đăng ký Passkey thành công!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val errorMessage = verifyResponse.body()?.message ?: "Xác thực Passkey thất bại"
                            Toast.makeText(
                                this@SettingsActivity,
                                errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@SettingsActivity,
                            "Lỗi khi xác thực Passkey: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    this@SettingsActivity,
                    "Tạo Passkey thất bại",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}



