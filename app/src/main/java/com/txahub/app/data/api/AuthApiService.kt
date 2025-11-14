package com.txahub.app.data.api

import com.txahub.app.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface AuthApiService {
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<AuthResponse>>
    
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<AuthResponse>>
    
    @POST("auth/verify-email")
    suspend fun verifyEmail(@Body request: VerifyEmailRequest): Response<ApiResponse<Unit>>
    
    @POST("auth/resend-verification")
    suspend fun resendVerification(@Body request: ResendVerificationRequest): Response<ApiResponse<Unit>>
    
    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ApiResponse<Unit>>
    
    @POST("auth/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ApiResponse<Unit>>
    
    @GET("user")
    suspend fun getUser(): Response<ApiResponse<User>>
    
    @POST("auth/logout")
    suspend fun logout(): Response<ApiResponse<Unit>>
}


