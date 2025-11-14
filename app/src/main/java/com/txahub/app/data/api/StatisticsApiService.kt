package com.txahub.app.data.api

import com.txahub.app.data.models.ApiResponse
import com.txahub.app.data.models.UserStatistics
import retrofit2.Response
import retrofit2.http.GET

interface StatisticsApiService {
    
    @GET("statistics/user")
    suspend fun getUserStatistics(): Response<ApiResponse<UserStatistics>>
}


