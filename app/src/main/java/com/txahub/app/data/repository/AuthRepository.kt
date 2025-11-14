package com.txahub.app.data.repository

import com.txahub.app.data.api.ApiClient
import com.txahub.app.data.local.PreferencesManager
import com.txahub.app.data.models.*
import kotlinx.coroutines.flow.first

class AuthRepository(
    private val preferencesManager: PreferencesManager
) {
    
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = ApiClient.authApi.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body()?.success == true) {
                val authResponse = response.body()!!.data!!
                // Lưu token và user info
                preferencesManager.saveAuthToken(authResponse.token)
                preferencesManager.saveUserInfo(
                    authResponse.user.email,
                    authResponse.user.name,
                    authResponse.user.id.toString(),
                    authResponse.user.isAdmin
                )
                ApiClient.setAuthToken(authResponse.token)
                Result.success(authResponse)
            } else {
                val errorMessage = response.body()?.message ?: "Đăng nhập thất bại"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun register(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ): Result<AuthResponse> {
        return try {
            val response = ApiClient.authApi.register(
                RegisterRequest(name, email, password, passwordConfirmation)
            )
            if (response.isSuccessful && response.body()?.success == true) {
                val authResponse = response.body()!!.data!!
                // Lưu token và user info
                preferencesManager.saveAuthToken(authResponse.token)
                preferencesManager.saveUserInfo(
                    authResponse.user.email,
                    authResponse.user.name,
                    authResponse.user.id.toString(),
                    authResponse.user.isAdmin
                )
                ApiClient.setAuthToken(authResponse.token)
                Result.success(authResponse)
            } else {
                val errorMessage = response.body()?.message 
                    ?: response.body()?.errors?.values?.flatten()?.joinToString(", ")
                    ?: "Đăng ký thất bại"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun verifyEmail(email: String, token: String): Result<Unit> {
        return try {
            val response = ApiClient.authApi.verifyEmail(VerifyEmailRequest(email, token))
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(Unit)
            } else {
                val errorMessage = response.body()?.message ?: "Xác minh email thất bại"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun resendVerification(email: String): Result<Unit> {
        return try {
            val response = ApiClient.authApi.resendVerification(ResendVerificationRequest(email))
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(Unit)
            } else {
                val errorMessage = response.body()?.message ?: "Gửi lại email thất bại"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun forgotPassword(email: String): Result<Unit> {
        return try {
            val response = ApiClient.authApi.forgotPassword(ForgotPasswordRequest(email))
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(Unit)
            } else {
                val errorMessage = response.body()?.message ?: "Gửi email đặt lại mật khẩu thất bại"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun resetPassword(
        email: String,
        token: String,
        password: String,
        passwordConfirmation: String
    ): Result<Unit> {
        return try {
            val response = ApiClient.authApi.resetPassword(
                ResetPasswordRequest(email, token, password, passwordConfirmation)
            )
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(Unit)
            } else {
                val errorMessage = response.body()?.message ?: "Đặt lại mật khẩu thất bại"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun logout() {
        try {
            ApiClient.authApi.logout()
        } catch (e: Exception) {
            // Ignore errors on logout
        } finally {
            preferencesManager.clearAll()
            ApiClient.clearAuthToken()
        }
    }
    
    suspend fun isLoggedIn(): Boolean {
        val token = preferencesManager.getAuthTokenSync()
        return !token.isNullOrEmpty()
    }
    
    suspend fun getCurrentUser(): User? {
        return try {
            val response = ApiClient.authApi.getUser()
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()!!.data
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun isAdmin(): Boolean {
        return preferencesManager.isAdmin.first()
    }
}


