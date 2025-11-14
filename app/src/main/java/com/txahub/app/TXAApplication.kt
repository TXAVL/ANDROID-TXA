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
    
    override fun attachBaseContext(base: Context) {
        val preferencesManager = PreferencesManager(base)
        val language = try {
            runBlocking {
                preferencesManager.getLanguageSync()
            }
        } catch (e: Exception) {
            LocaleHelper.LANGUAGE_AUTO
        }
        super.attachBaseContext(LocaleHelper.setLocale(base, language))
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
        // Tự động tìm tất cả file nhạc chuông trong res/raw
        // Nếu có >= 2 file, random chọn 1 file để khởi tạo
        // Nếu có 1 file, dùng file đó
        try {
            val rawFiles = findRawSoundFiles()
            if (rawFiles.isNotEmpty()) {
                val selectedFile = if (rawFiles.size >= 2) {
                    // Random chọn 1 file nếu có >= 2 file
                    rawFiles.random()
                } else {
                    // Dùng file duy nhất nếu chỉ có 1 file
                    rawFiles[0]
                }
                
                val resourceId = resources.getIdentifier(selectedFile.first, "raw", packageName)
                if (resourceId != 0) {
                    soundManager.initializeDefaultAppSound(resourceId, selectedFile.second)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("TXAApplication", "Error initializing default app sound", e)
        }
        
        // Thêm tất cả nhạc chuông vào MediaStore
        soundManager.addAllAppSoundsToMediaStore()
        
        // Khởi động UpdateCheckService nếu có quyền battery optimization
        if (UpdateCheckService.hasBatteryOptimizationPermission(this)) {
            UpdateCheckService.startIfAllowed(this)
        }
    }
    
    /**
     * Tìm tất cả file nhạc chuông trong res/raw
     * @return List<Pair<resourceName, fileName>> - Tên resource (không có extension) và tên file đầy đủ
     */
    private fun findRawSoundFiles(): List<Pair<String, String>> {
        val rawFiles = mutableListOf<Pair<String, String>>()
        try {
            val fieldIds = R.raw::class.java.fields
            for (field in fieldIds) {
                val resourceName = field.name
                // Trong Android, tên resource không có extension
                // Ví dụ: file "chuong.mp3" sẽ có resource name là "chuong"
                val resourceId = resources.getIdentifier(resourceName, "raw", packageName)
                if (resourceId != 0) {
                    // Tìm thấy resource, giả sử extension là .mp3 (phổ biến nhất)
                    // Nếu file thực tế có extension khác, có thể cần điều chỉnh sau
                    val fileName = "${resourceName}.mp3"
                    rawFiles.add(Pair(resourceName, fileName))
                }
            }
            android.util.Log.d("TXAApplication", "Found ${rawFiles.size} raw sound files: ${rawFiles.map { it.second }}")
        } catch (e: Exception) {
            android.util.Log.e("TXAApplication", "Error finding raw sound files", e)
        }
        return rawFiles
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


