package com.txahub.app.data.repository

import com.txahub.app.data.api.ApiClient
import com.txahub.app.data.models.UserStatistics

class StatisticsRepository {
    
    suspend fun getUserStatistics(): Result<UserStatistics> {
        return try {
            val response = ApiClient.statisticsApi.getUserStatistics()
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()!!.data!!)
            } else {
                val errorMessage = response.body()?.message ?: "Lấy thống kê thất bại"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


