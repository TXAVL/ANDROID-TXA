package com.txahub.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.txahub.app.R
import com.txahub.app.databinding.ActivityLoginBinding
import com.txahub.app.data.local.PreferencesManager
import com.txahub.app.data.repository.AuthRepository
import com.txahub.app.data.api.ApiClient
import com.txahub.app.ui.MainActivity
import com.txahub.app.utils.PasskeyManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var passkeyManager: PasskeyManager
    private var isRegisterMode = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        preferencesManager = PreferencesManager(this)
        authRepository = AuthRepository(preferencesManager)
        passkeyManager = PasskeyManager(this)
        
        setupUI()
    }
    
    private fun setupUI() {
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
            handlePasskeyLogin()
        }
        
        // Ẩn nút passkey nếu thiết bị không hỗ trợ
        if (!passkeyManager.isPasskeySupported()) {
            binding.btnPasskeyLogin.visibility = android.view.View.GONE
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
            
            result.onSuccess { authResponse ->
                if (rememberMe) {
                    preferencesManager.setRememberMe(true)
                }
                
                // Kiểm tra email đã xác minh chưa
                if (authResponse.user.emailVerifiedAt == null) {
                    showEmailVerificationDialog(email)
                } else {
                    navigateToMain()
                }
            }.onFailure { error ->
                Toast.makeText(
                    this@LoginActivity,
                    error.message ?: getString(R.string.txa_global_error_invalid_credentials),
                    Toast.LENGTH_LONG
                ).show()
            }
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
            
            result.onSuccess { authResponse ->
                // Sau khi đăng ký thành công, hiển thị thông báo xác minh email
                showEmailVerificationRequiredDialog(email)
            }.onFailure { error ->
                Toast.makeText(
                    this@LoginActivity,
                    error.message ?: getString(R.string.txa_global_error_register_failed),
                    Toast.LENGTH_LONG
                ).show()
            }
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
            result.onSuccess {
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.txa_global_verification_sent),
                    Toast.LENGTH_LONG
                ).show()
            }.onFailure { error ->
                Toast.makeText(
                    this@LoginActivity,
                    error.message ?: getString(R.string.txa_global_error_resend_failed),
                    Toast.LENGTH_LONG
                ).show()
            }
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
            result.onSuccess {
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.txa_global_reset_password_sent, email),
                    Toast.LENGTH_LONG
                ).show()
            }.onFailure { error ->
                Toast.makeText(
                    this@LoginActivity,
                    error.message ?: getString(R.string.txa_global_error_forgot_password_failed),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    
    private fun handlePasskeyLogin() {
        if (!passkeyManager.isPasskeySupported()) {
            Toast.makeText(
                this,
                getString(R.string.txa_global_passkey_not_supported),
                Toast.LENGTH_LONG
            ).show()
            return
        }
        
        binding.btnPasskeyLogin.isEnabled = false
        binding.progressBar.visibility = android.view.View.VISIBLE
        
        lifecycleScope.launch {
            val result = passkeyManager.authenticatePasskey()
            
            binding.btnPasskeyLogin.isEnabled = true
            binding.progressBar.visibility = android.view.View.GONE
            
            result.onSuccess { authResponse ->
                // Lưu token và user info nếu có
                authResponse.data?.let { authData ->
                    preferencesManager.saveAuthToken(authData.token)
                    preferencesManager.saveUserInfo(
                        authData.user.email,
                        authData.user.name,
                        authData.user.id.toString(),
                        authData.user.isAdmin
                    )
                    ApiClient.setAuthToken(authData.token)
                }
                
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.txa_global_passkey_login_success),
                    Toast.LENGTH_SHORT
                ).show()
                
                navigateToMain()
            }.onFailure { error ->
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.txa_global_passkey_login_failed, error.message ?: "Unknown error"),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    
    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}



