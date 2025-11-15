package com.txahub.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.txahub.app.R
import com.txahub.app.databinding.ActivityLoginBinding
import com.txahub.app.data.local.PreferencesManager
import com.txahub.app.data.repository.AuthRepository
import com.txahub.app.data.api.ApiClient
import com.txahub.app.data.models.PasskeyModels
import com.txahub.app.data.models.AuthResponse
import com.txahub.app.ui.MainActivity
import com.txahub.app.utils.ChangelogActivity
import com.txahub.app.ui.SettingsActivity
import com.txahub.app.utils.PasskeyManager
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var passkeyManager: PasskeyManager
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private var isRegisterMode = false
    private var currentChallengeId: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        preferencesManager = PreferencesManager(this)
        authRepository = AuthRepository(preferencesManager)
        passkeyManager = PasskeyManager(this)
        passkeyManager.setCallback(object : PasskeyManager.PasskeyCallback {
            override fun onSuccess(data: JSONObject) {
                // Xử lý khi Passkey thành công
                runOnUiThread {
                    handlePasskeySuccess(data)
                }
            }
            
            override fun onError(code: String, message: String) {
                // Xử lý khi Passkey lỗi
                runOnUiThread {
                    Toast.makeText(
                        this@LoginActivity,
                        "Passkey error: $message",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
        
        setupUI()
        setupNavigationDrawer()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        
        binding.btnToggleMode.setOnClickListener {
            toggleMode()
        }
        
        binding.btnSubmit.setOnClickListener {
            if (isRegisterMode) {
                handleRegister()
            } else {
                handleLogin()
            }
        }
        
        binding.tvForgotPassword.setOnClickListener {
            showForgotPasswordDialog()
        }
        
        // Setup Passkey login button
        binding.btnPasskeyLogin.setOnClickListener {
            if (passkeyManager.isPasskeySupported()) {
                handlePasskeyLogin()
            } else {
                Toast.makeText(
                    this,
                    "Thiết bị không hỗ trợ Passkey",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    
    private fun setupNavigationDrawer() {
        // Setup drawer toggle với menu hamburger
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.txa_global_menu_dashboard,
            R.string.txa_global_menu_dashboard
        )
        
        // Enable hamburger icon
        drawerToggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        
        // Setup navigation item click listener
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationItemSelected(menuItem)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        
        // Set dashboard as selected by default
        binding.navigationView.setCheckedItem(R.id.nav_dashboard)
    }
    
    private fun handleNavigationItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.nav_dashboard -> {
                // Đã ở màn hình login, không cần làm gì
                true
            }
            R.id.nav_changelog -> {
                startActivity(Intent(this, ChangelogActivity::class.java))
                true
            }
            R.id.nav_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> false
        }
    }
    
    private fun toggleMode() {
        isRegisterMode = !isRegisterMode
        
        if (isRegisterMode) {
            // Chuyển sang chế độ đăng ký
            binding.tilName.visibility = android.view.View.VISIBLE
            binding.tilConfirmPassword.visibility = android.view.View.VISIBLE
            binding.btnSubmit.text = getString(R.string.txa_global_register)
            binding.btnToggleMode.text = getString(R.string.txa_global_already_have_account)
            binding.tvTitle.text = getString(R.string.txa_global_register)
        } else {
            // Chuyển sang chế độ đăng nhập
            binding.tilName.visibility = android.view.View.GONE
            binding.tilConfirmPassword.visibility = android.view.View.GONE
            binding.btnSubmit.text = getString(R.string.txa_global_login)
            binding.btnToggleMode.text = getString(R.string.txa_global_dont_have_account)
            binding.tvTitle.text = getString(R.string.txa_global_login)
        }
    }
    
    private fun handleLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val rememberMe = binding.cbRememberMe.isChecked
        
        if (!validateLoginInput(email, password)) {
            return
        }
        
        binding.btnSubmit.isEnabled = false
        binding.progressBar.visibility = android.view.View.VISIBLE
        
        lifecycleScope.launch {
            val result = authRepository.login(email, password)
            
            binding.btnSubmit.isEnabled = true
            binding.progressBar.visibility = android.view.View.GONE
            
            result.fold(
                onSuccess = { authResponse ->
                    if (rememberMe) {
                        preferencesManager.setRememberMe(true)
                    }
                    
                    // Ghi log sau khi đăng nhập thành công
                    val logWriter = com.txahub.app.utils.LogWriter(this@LoginActivity)
                    logWriter.writeAppLog(
                        "=== Login Success ===\n" +
                        "Email: ${authResponse.user.email}\n" +
                        "Name: ${authResponse.user.name}\n" +
                        "User ID: ${authResponse.user.id}\n" +
                        "Is Admin: ${authResponse.user.isAdmin}\n" +
                        "Email Verified: ${authResponse.user.emailVerifiedAt != null}\n" +
                        "Token: ${authResponse.token.take(20)}...",
                        "LoginActivity",
                        android.util.Log.INFO
                    )
                    
                    // Kiểm tra email đã xác minh chưa
                    if (authResponse.user.emailVerifiedAt == null) {
                        showEmailVerificationDialog(email)
                    } else {
                        navigateToMain()
                    }
                },
                onFailure = { error ->
                    Toast.makeText(
                        this@LoginActivity,
                        error.message ?: getString(R.string.txa_global_error_invalid_credentials),
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }
    
    private fun handleRegister() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        
        if (!validateRegisterInput(name, email, password, confirmPassword)) {
            return
        }
        
        binding.btnSubmit.isEnabled = false
        binding.progressBar.visibility = android.view.View.VISIBLE
        
        lifecycleScope.launch {
            val result = authRepository.register(name, email, password, confirmPassword)
            
            binding.btnSubmit.isEnabled = true
            binding.progressBar.visibility = android.view.View.GONE
            
            result.fold(
                onSuccess = { _ ->
                    // Sau khi đăng ký thành công, hiển thị thông báo xác minh email
                    showEmailVerificationRequiredDialog(email)
                },
                onFailure = { error ->
                    Toast.makeText(
                        this@LoginActivity,
                        error.message ?: getString(R.string.txa_global_error_register_failed),
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }
    
    private fun validateLoginInput(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = getString(R.string.txa_global_error_invalid_email)
            return false
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = getString(R.string.txa_global_error_invalid_email)
            return false
        }
        
        if (password.length < 6) {
            binding.etPassword.error = getString(R.string.txa_global_error_weak_password)
            return false
        }
        
        return true
    }
    
    private fun validateRegisterInput(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (TextUtils.isEmpty(name)) {
            binding.etName.error = "Vui lòng nhập tên"
            return false
        }
        
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = getString(R.string.txa_global_error_invalid_email)
            return false
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = getString(R.string.txa_global_error_invalid_email)
            return false
        }
        
        if (password.length < 6) {
            binding.etPassword.error = getString(R.string.txa_global_error_weak_password)
            return false
        }
        
        if (password != confirmPassword) {
            binding.etConfirmPassword.error = getString(R.string.txa_global_error_password_mismatch)
            return false
        }
        
        return true
    }
    
    private fun showEmailVerificationRequiredDialog(email: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.txa_global_email_verification_required))
            .setMessage(getString(R.string.txa_global_verification_message, email))
            .setPositiveButton(getString(R.string.txa_global_ok)) { _, _ ->
                // Chuyển về chế độ đăng nhập
                isRegisterMode = false
                toggleMode()
            }
            .setNeutralButton(getString(R.string.txa_global_resend_verification)) { _, _ ->
                resendVerification(email)
            }
            .setCancelable(false)
            .show()
    }
    
    private fun showEmailVerificationDialog(email: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.txa_global_email_verification_required))
            .setMessage(getString(R.string.txa_global_verification_not_verified))
            .setPositiveButton(getString(R.string.txa_global_ok)) { _, _ ->
                navigateToMain()
            }
            .setNeutralButton(getString(R.string.txa_global_resend_verification)) { _, _ ->
                resendVerification(email)
            }
            .show()
    }
    
    private fun resendVerification(email: String) {
        lifecycleScope.launch {
            val result = authRepository.resendVerification(email)
            result.fold(
                onSuccess = {
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.txa_global_verification_sent),
                        Toast.LENGTH_LONG
                    ).show()
                },
                onFailure = { error ->
                    Toast.makeText(
                        this@LoginActivity,
                        error.message ?: getString(R.string.txa_global_error_resend_failed),
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }
    
    private fun showForgotPasswordDialog() {
        val emailEditText = android.widget.EditText(this)
        emailEditText.hint = getString(R.string.txa_global_email)
        emailEditText.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.txa_global_forgot_password_title))
            .setMessage(getString(R.string.txa_global_forgot_password_message))
            .setView(emailEditText)
            .setPositiveButton(getString(R.string.txa_global_ok)) { _, _ ->
                val email = emailEditText.text.toString().trim()
                if (email.isNotEmpty()) {
                    sendForgotPasswordEmail(email)
                }
            }
            .setNegativeButton(getString(R.string.txa_global_cancel), null)
            .show()
    }
    
    private fun sendForgotPasswordEmail(email: String) {
        lifecycleScope.launch {
            val result = authRepository.forgotPassword(email)
            result.fold(
                onSuccess = {
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.txa_global_reset_password_sent, email),
                        Toast.LENGTH_LONG
                    ).show()
                },
                onFailure = { error ->
                    Toast.makeText(
                        this@LoginActivity,
                        error.message ?: getString(R.string.txa_global_error_forgot_password_failed),
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }
    
    
    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    
    private fun handlePasskeyLogin() {
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.btnPasskeyLogin.isEnabled = false
        
        lifecycleScope.launch {
            try {
                // Log bắt đầu tạo challenge
                val logWriter = com.txahub.app.utils.LogWriter(this@LoginActivity)
                logWriter.writePasskeyLog("=== Starting Passkey Authentication ===", "INFO")
                logWriter.writePasskeyLog("Requesting challenge for authentication", "DEBUG")
                
                // Log full URL trước khi gọi API
                val createChallengeUrl = "https://txahub.click/api/passkey-api/create-challenge"
                logWriter.writePasskeyLog("API URL: $createChallengeUrl", "DEBUG")
                logWriter.writePasskeyLog("Request Type: authentication", "DEBUG")
                
                // Gọi API để lấy challenge
                val response = ApiClient.passkeyApi.createChallenge(
                    PasskeyModels.CreateChallengeRequest("authentication")
                )
                
                // Log response
                logWriter.writePasskeyLog("API Response - Code: ${response.code()}, Success: ${response.isSuccessful}", "DEBUG")
                
                if (!response.isSuccessful) {
                    val errorBody = try {
                        response.errorBody()?.string() ?: "No error body"
                    } catch (e: Exception) {
                        "Cannot read error body: ${e.message}"
                    }
                    logWriter.writePasskeyLog("API Error - Code: ${response.code()}, Body: $errorBody", "ERROR")
                }
                
                if (response.isSuccessful && response.body()?.success == true) {
                    val challengeData = response.body()!!.data!!
                    
                    logWriter.writePasskeyLog("Challenge received - ID: ${challengeData.challengeId}, RP: ${challengeData.rp.id}", "INFO")
                    
                    // Lưu challenge_id để dùng khi verify
                    currentChallengeId = challengeData.challengeId
                    
                    // Build config JSON cho getPasskey
                    val config = JSONObject().apply {
                        put("challenge", challengeData.challenge)
                        put("rpId", challengeData.rp.id)
                        put("timeout", challengeData.timeout)
                        put("userVerification", challengeData.userVerification)
                        put("origin", "https://txahub.click")
                        put("hostname", challengeData.rp.id)
                    }
                    
                    logWriter.writePasskeyLog("Calling PasskeyManager.getPasskey()", "DEBUG")
                    
                    // Gọi PasskeyManager để lấy Passkey
                    passkeyManager.getPasskey(config.toString(), "onPasskeyRetrieved", this@LoginActivity)
                } else {
                    val errorMessage = response.body()?.message ?: "Không thể tạo challenge"
                    val responseCode = response.code()
                    val fullError = "Error: $errorMessage (Code: $responseCode)"
                    
                    logWriter.writePasskeyLog(fullError, "ERROR")
                    if (response.errorBody() != null) {
                        try {
                            val errorBody = response.errorBody()!!.string()
                            logWriter.writePasskeyLog("Error Body: $errorBody", "ERROR")
                        } catch (e: Exception) {
                            logWriter.writePasskeyLog("Cannot read error body: ${e.message}", "ERROR")
                        }
                    }
                    
                    Toast.makeText(
                        this@LoginActivity,
                        errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.btnPasskeyLogin.isEnabled = true
                }
            } catch (e: Exception) {
                val logWriter = com.txahub.app.utils.LogWriter(this@LoginActivity)
                logWriter.writePasskeyLog("Exception: ${e.message}\n${e.stackTraceToString()}", "ERROR")
                
                Toast.makeText(
                    this@LoginActivity,
                    "Lỗi: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                binding.progressBar.visibility = android.view.View.GONE
                binding.btnPasskeyLogin.isEnabled = true
            }
        }
    }
    
    private fun handlePasskeySuccess(data: JSONObject) {
        binding.progressBar.visibility = android.view.View.VISIBLE
        
        lifecycleScope.launch {
            try {
                // Lấy challenge_id đã lưu
                val challengeId = currentChallengeId
                if (challengeId == null) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Lỗi: Không tìm thấy challenge ID",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.btnPasskeyLogin.isEnabled = true
                    return@launch
                }
                
                // Log full URL trước khi gọi API verify
                val logWriter = com.txahub.app.utils.LogWriter(this@LoginActivity)
                val verifyAuthUrl = "https://txahub.click/api/passkey-api/verify-authentication"
                logWriter.writePasskeyLog("=== Verifying Passkey Authentication ===", "INFO")
                logWriter.writePasskeyLog("API URL: $verifyAuthUrl", "DEBUG")
                logWriter.writePasskeyLog("Challenge ID: $challengeId", "DEBUG")
                
                // Gọi API để verify authentication
                val response = ApiClient.passkeyApi.verifyAuthentication(
                    PasskeyModels.VerifyAuthenticationRequest(
                        challengeId = challengeId,
                        credential = data.toString()
                    )
                )
                
                // Log response
                logWriter.writePasskeyLog("API Response - Code: ${response.code()}, Success: ${response.isSuccessful}", "DEBUG")
                
                if (!response.isSuccessful) {
                    val errorBody = try {
                        response.errorBody()?.string() ?: "No error body"
                    } catch (e: Exception) {
                        "Cannot read error body: ${e.message}"
                    }
                    logWriter.writePasskeyLog("API Error - Code: ${response.code()}, Body: $errorBody", "ERROR")
                }
                
                binding.progressBar.visibility = android.view.View.GONE
                binding.btnPasskeyLogin.isEnabled = true
                
                if (response.isSuccessful && response.body()?.success == true) {
                    // Theo docs, verify_authentication chỉ trả về success và message
                    // Server tự động tạo session, nhưng có thể trả về token trong data (optional)
                    val verifyResponse = response.body()?.data // ApiResponse.data = VerifyAuthenticationResponse?
                    val authResponse: AuthResponse? = verifyResponse?.data // VerifyAuthenticationResponse.data = AuthResponse?
                    
                    if (authResponse != null) {
                        // Nếu có data (token), lưu ngay
                        preferencesManager.saveAuthToken(authResponse.token)
                        preferencesManager.saveUserInfo(
                            authResponse.user.email,
                            authResponse.user.name,
                            authResponse.user.id.toString(),
                            authResponse.user.isAdmin
                        )
                        ApiClient.setAuthToken(authResponse.token)
                        
                        // Chuyển đến MainActivity
                        navigateToMain()
                    } else {
                        try {
                            // Thử gọi getUser() - server có thể dùng session để xác thực
                            val userResponse = ApiClient.authApi.getUser()
                            if (userResponse.isSuccessful && userResponse.body()?.success == true) {
                                val user = userResponse.body()!!.data
                                if (user != null) {
                                    // Lấy api_key từ user để dùng làm token
                                    val token = user.apiKey
                                    if (!token.isNullOrEmpty()) {
                                        preferencesManager.saveAuthToken(token)
                                        preferencesManager.saveUserInfo(
                                            user.email,
                                            user.name,
                                            user.id.toString(),
                                            user.isAdmin
                                        )
                                        ApiClient.setAuthToken(token)
                                        
                                        // Chuyển đến MainActivity
                                        navigateToMain()
                                    } else {
                                        // Nếu không có api_key, hiển thị thông báo
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "Xác thực thành công nhưng không thể lấy token. Vui lòng đăng nhập lại.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Không thể lấy thông tin user",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                // Nếu getUser() fail, có thể cần token để gọi API
                                // Hiển thị thông báo yêu cầu server trả về token trong response
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Xác thực thành công. Vui lòng đăng nhập lại để lấy token.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Lỗi khi lấy thông tin user: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    val errorMessage = response.body()?.message ?: "Xác thực Passkey thất bại"
                    Toast.makeText(
                        this@LoginActivity,
                        errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                binding.progressBar.visibility = android.view.View.GONE
                binding.btnPasskeyLogin.isEnabled = true
                Toast.makeText(
                    this@LoginActivity,
                    "Lỗi: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        if (::passkeyManager.isInitialized) {
            passkeyManager.cleanup()
        }
    }
}



