package com.txahub.app.data.api

import android.content.Context
import com.txahub.app.utils.LogWriter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "https://txahub.click/api/"
    
    private var authToken: String? = null
    private var context: Context? = null
    
    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val apiLoggingInterceptor = Interceptor { chain ->
        val request = chain.request()
        val startTime = System.currentTimeMillis()
        
        // Log request body nếu có
        var requestBodyString = ""
        request.body?.let { body ->
            try {
                val buffer = okio.Buffer()
                body.writeTo(buffer)
                requestBodyString = buffer.readUtf8()
            } catch (e: Exception) {
                requestBodyString = "[Unable to read request body: ${e.message}]"
            }
        }
        
        val response = chain.proceed(request)
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        
        // Ghi log API
        context?.let { ctx ->
            val logWriter = LogWriter(ctx)
            val logData = buildString {
                append("=== API Request ===\n")
                append("Method: ${request.method}\n")
                append("URL: ${request.url}\n")
                append("Headers: ${request.headers}\n")
                if (requestBodyString.isNotEmpty()) {
                    append("Body: $requestBodyString\n")
                }
                append("\n=== API Response ===\n")
                append("Status: ${response.code} ${response.message}\n")
                append("Duration: ${duration}ms\n")
                append("Headers: ${response.headers}\n")
                try {
                    val responseBody = response.peekBody(1024 * 1024) // Peek 1MB
                    append("Body: ${responseBody.string()}\n")
                } catch (e: Exception) {
                    append("Body: [Unable to read response body: ${e.message}]\n")
                }
                append("=== End Log ===\n\n")
            }
            logWriter.writeApiLog(logData, request.url.toString())
        }
        
        response
    }
    
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = if (authToken != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $authToken")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build()
        } else {
            originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build()
        }
        chain.proceed(newRequest)
    }
    
    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        
        // Thêm API logging interceptor nếu context đã được khởi tạo
        if (context != null) {
            builder.addInterceptor(apiLoggingInterceptor)
        }
        
        return builder.build()
    }
    
    private val okHttpClient = createOkHttpClient()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val authApi: AuthApiService = retrofit.create(AuthApiService::class.java)
    val statisticsApi: StatisticsApiService = retrofit.create(StatisticsApiService::class.java)
    val passkeyApi: PasskeyApiService = retrofit.create(PasskeyApiService::class.java)
    
    fun setAuthToken(token: String) {
        authToken = token
    }
    
    fun clearAuthToken() {
        authToken = null
    }
}


