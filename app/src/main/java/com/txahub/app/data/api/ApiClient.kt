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
        val urlString = request.url.toString()
        
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
        
        // Đọc response body - đảm bảo luôn đọc được
        val responseBodyString = try {
            val responseBody = response.peekBody(1024 * 1024) // Peek 1MB
            val bodyString = responseBody.string()
            if (bodyString.isBlank()) {
                "[Response body is empty]"
            } else {
                bodyString
            }
        } catch (e: Exception) {
            "[Unable to read response body: ${e.message}]"
        }
        
        // Ghi log API
        context?.let { ctx ->
            val logWriter = LogWriter(ctx)
            
            // Kiểm tra xem có phải login/register/forgot-password/reset-password API không
            val isLoginApi = urlString.contains("/auth/login") || 
                           urlString.contains("/auth/register") ||
                           urlString.contains("/auth/forgot-password") ||
                           urlString.contains("/auth/reset-password") ||
                           urlString.contains("/auth/resend-verification") ||
                           urlString.contains("/auth/verify-email")
            
            // Kiểm tra xem có phải passkey API không
            val isPasskeyApi = urlString.contains("/passkey-api/")
            
            if (isLoginApi) {
                // Log vào file login riêng
                logWriter.writeLoginLog(
                    method = request.method,
                    url = urlString,
                    requestBody = requestBodyString,
                    responseCode = response.code,
                    responseBody = responseBodyString,
                    duration = duration
                )
            } else if (isPasskeyApi) {
                // Log vào file passkey riêng
                logWriter.writePasskeyApiLog(
                    method = request.method,
                    url = urlString,
                    requestBody = requestBodyString,
                    responseCode = response.code,
                    responseBody = responseBodyString,
                    duration = duration
                )
            }
            
            // Vẫn log vào file API chung (nếu cần)
            val logData = buildString {
                append("=== API Request ===\n")
                append("Method: ${request.method}\n")
                append("URL: $urlString\n")
                append("Headers: ${request.headers}\n")
                if (requestBodyString.isNotEmpty()) {
                    append("Body: $requestBodyString\n")
                }
                append("\n=== API Response ===\n")
                append("Status: ${response.code} ${response.message}\n")
                append("Duration: ${duration}ms\n")
                append("Headers: ${response.headers}\n")
                append("Body: $responseBodyString\n")
                append("=== End Log ===\n\n")
            }
            logWriter.writeApiLog(logData, urlString)
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


