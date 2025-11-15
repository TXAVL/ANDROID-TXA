package com.txahub.app.data.api

import com.txahub.app.data.models.PasskeyModels
import com.txahub.app.data.models.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PasskeyApiService {
    
    @POST("passkey-api/create_challenge")
    suspend fun createChallenge(
        @Body request: PasskeyModels.CreateChallengeRequest
    ): Response<ApiResponse<PasskeyModels.CreateChallengeResponse>>
    
    @POST("passkey-api/verify_registration")
    suspend fun verifyRegistration(
        @Body request: PasskeyModels.VerifyRegistrationRequest
    ): Response<ApiResponse<PasskeyModels.VerifyRegistrationResponse>>
    
    @POST("passkey-api/verify_authentication")
    suspend fun verifyAuthentication(
        @Body request: PasskeyModels.VerifyAuthenticationRequest
    ): Response<ApiResponse<PasskeyModels.VerifyAuthenticationResponse>>
}
