package com.txahub.app.data.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String
)

data class AuthResponse(
    val token: String,
    val user: User
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerializedName("is_admin")
    val isAdmin: Boolean = false,
    @SerializedName("api_key")
    val apiKey: String? = null, // API key để dùng làm token
    val license: License? = null
)

data class License(
    val type: String,
    @SerializedName("is_pro")
    val isPro: Boolean = false,
    @SerializedName("is_vip")
    val isVip: Boolean = false,
    @SerializedName("restricted_features")
    val restrictedFeatures: List<String> = emptyList()
)

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null,
    val errors: Map<String, List<String>>? = null
)

data class VerifyEmailRequest(
    val email: String,
    val token: String
)

data class ResendVerificationRequest(
    val email: String
)

data class ForgotPasswordRequest(
    val email: String
)

data class ResetPasswordRequest(
    val email: String,
    val token: String,
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String
)


