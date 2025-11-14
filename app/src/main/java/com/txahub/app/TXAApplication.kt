package com.txahub.app

import android.app.Application
import android.content.Context
import com.txahub.app.data.api.ApiClient
import com.txahub.app.data.local.PreferencesManager
import com.txahub.app.utils.LocaleHelper
import com.txahub.app.utils.NotificationSoundManager
import com.txahub.app.utils.NotificationHelper
import com.txahub.app.utils.UpdateCheckService
import com.txahub.app.utils.LogWriter
import java.lang.Thread.UncaughtExceptionHandler
import kotlinx.coroutines.runBlocking

class TXAApplication : Application() {
    
    private val defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
    
    override fun attachBaseContext(base: Context): Context {
        val preferencesManager = PreferencesManager(base)
        val language = try {
            runBlocking {
                preferencesManager.getLanguageSync()
            }
        } catch (e: Exception) {
            LocaleHelper.LANGUAGE_AUTO
        }
        return LocaleHelper.setLocale(base, language)
    }
    
    override fun onCreate() {
        super.onCreate()
        
        // Khởi tạo ApiClient
        ApiClient.initialize(this)
        
        // Setup crash handler
        setupCrashHandler()
        
        // Khởi tạo notification channels
        NotificationHelper(this)
        
        // Khởi tạo và thêm nhạc chuông vào MediaStore
        val soundManager = NotificationSoundManager(this)
        // Nếu có file nhạc chuông trong raw, copy vào external storage và thêm vào MediaStore
        // Ví dụ: R.raw.chuong (nếu có)
        try {
            val resourceId = resources.getIdentifier("chuong", "raw", packageName)
            if (resourceId != 0) {
                soundManager.initializeDefaultAppSound(resourceId, "chuong.mp3")
            }
        } catch (e: Exception) {
            // Không có file raw, bỏ qua
        }
        
        // Thêm tất cả nhạc chuông vào MediaStore
        soundManager.addAllAppSoundsToMediaStore()
        
        // Khởi động UpdateCheckService nếu có quyền battery optimization
        if (UpdateCheckService.hasBatteryOptimizationPermission(this)) {
            UpdateCheckService.startIfAllowed(this)
        }
    }
    
    private fun setupCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            try {
                val logWriter = LogWriter(this)
                val crashInfo = buildString {
                    append("=== CRASH REPORT ===\n")
                    append("Time: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}\n")
                    append("Thread: ${thread.name}\n")
                    append("Exception: ${exception.javaClass.name}\n")
                    append("Message: ${exception.message}\n")
                    append("\nStack Trace:\n")
                    append(exception.stackTraceToString())
                    append("\n\n=== END CRASH REPORT ===\n")
                }
                logWriter.writeCrashLog(crashInfo)
            } catch (e: Exception) {
                android.util.Log.e("TXAApplication", "Failed to write crash log", e)
            }
            
            // Gọi default handler
            defaultExceptionHandler?.uncaughtException(thread, exception)
        }
    }
}


