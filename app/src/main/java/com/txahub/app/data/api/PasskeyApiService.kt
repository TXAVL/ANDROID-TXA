package com.txahub.app.data.api

import com.txahub.app.data.models.PasskeyModels
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PasskeyApiService {
    
    /**
     * Tạo challenge cho passkey registration hoặc authentication
     */
    @POST("passkey-api/create_challenge")
    suspend fun createChallenge(
        @Body request: PasskeyModels.CreateChallengeRequest
    ): Response<PasskeyModels.CreateChallengeResponse>
    
    /**
     * Xác thực passkey registration
     */
    @POST("passkey-api/verify_registration")
    suspend fun verifyRegistration(
        @Body request: PasskeyModels.VerifyRegistrationRequest
    ): Response<PasskeyModels.VerifyRegistrationResponse>
    
    /**
     * Xác thực passkey để đăng nhập
     */
    @POST("passkey-api/verify_authentication")
    suspend fun verifyAuthentication(
        @Body request: PasskeyModels.VerifyAuthenticationRequest
    ): Response<PasskeyModels.VerifyAuthenticationResponse>
}

