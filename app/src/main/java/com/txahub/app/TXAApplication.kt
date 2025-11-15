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
import com.txahub.app.utils.LogSettingsManager
import java.lang.Thread.UncaughtExceptionHandler
import kotlinx.coroutines.runBlocking

class TXAApplication : Application() {
    
    private val defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
    
    override fun attachBaseContext(base: Context) {
        try {
            // Setup crash handler sớm nhất có thể (ngay sau khi có context)
            setupEarlyCrashHandler(base)
            
            val preferencesManager = PreferencesManager(base)
            val language = try {
                runBlocking { 
                    preferencesManager.getLanguageSync()
                }
            } catch (e: Exception) {
                logError("TXAApplication", "Error getting language in attachBaseContext", e, base)
                LocaleHelper.LANGUAGE_AUTO
            }
            super.attachBaseContext(LocaleHelper.setLocale(base, language))
        } catch (e: Exception) {
            // Log crash ngay cả trong attachBaseContext
            logError("TXAApplication", "Critical error in attachBaseContext", e, base)
            // Vẫn gọi super để app không crash hoàn toàn
            try {
                super.attachBaseContext(base)
            } catch (e2: Exception) {
                android.util.Log.e("TXAApplication", "Fatal error in attachBaseContext", e2)
            }
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        
        try {
            // Setup crash handler ngay đầu onCreate (sau khi đã có Application context)
            setupCrashHandler()
        } catch (e: Exception) {
            android.util.Log.e("TXAApplication", "Error setting up crash handler", e)
            // Vẫn tiếp tục để app có thể chạy
        }
        
        try {
            // Bật tất cả log settings theo mặc định (ghi đè lên setting hiện tại)
            val logSettingsManager = LogSettingsManager(this)
            logSettingsManager.enableAllLogs() // Đảm bảo tất cả log đều bật
        } catch (e: Exception) {
            android.util.Log.e("TXAApplication", "Error initializing log settings", e)
            // Vẫn tiếp tục để app có thể chạy
        }
        
        try {
            // Khởi tạo ApiClient
            ApiClient.initialize(this)
        } catch (e: Exception) {
            logError("TXAApplication", "Error initializing ApiClient", e, this)
            // Không throw để app vẫn có thể chạy
        }
        
        try {
            // Khởi tạo notification channels
            NotificationHelper(this)
        } catch (e: Exception) {
            logError("TXAApplication", "Error initializing NotificationHelper", e, this)
        }
        
        try {
            // Khởi tạo và thêm nhạc chuông vào MediaStore
            val soundManager = NotificationSoundManager(this)
            // Tự động tìm tất cả file nhạc chuông trong res/raw
            // Copy TẤT CẢ file vào external storage (không random)
            // Sau đó random chọn 1 file để set làm default
            try {
                val rawFiles = findRawSoundFiles()
                if (rawFiles.isNotEmpty()) {
                    // Copy TẤT CẢ file từ res/raw vào external storage
                    for (rawFile in rawFiles) {
                        val resourceId = resources.getIdentifier(rawFile.first, "raw", packageName)
                        if (resourceId != 0) {
                            soundManager.initializeDefaultAppSound(resourceId, rawFile.second)
                        }
                    }
                    
                    // Sau khi copy tất cả, random chọn 1 file để set làm default
                    val baseDir = getExternalFilesDir(null) ?: filesDir
                    val soundFolder = java.io.File(baseDir, "notification_sounds")
                    val soundFiles = soundFolder.listFiles { _, name ->
                        name.endsWith(".mp3", ignoreCase = true) || name.endsWith(".wav", ignoreCase = true)
                    }?.filter { it.isFile } ?: emptyList()
                    
                    if (soundFiles.isNotEmpty()) {
                        // Random chọn 1 file
                        val selectedFile = if (soundFiles.size > 1) {
                            soundFiles.random()
                        } else {
                            soundFiles[0]
                        }
                        
                        // Tạo URI và set làm default
                        val selectedUri = try {
                            androidx.core.content.FileProvider.getUriForFile(
                                this,
                                "${packageName}.fileprovider",
                                selectedFile
                            )
                        } catch (e: Exception) {
                            android.net.Uri.fromFile(selectedFile)
                        }
                        
                        soundManager.setDefaultAppSoundUri(selectedUri)
                        android.util.Log.d("TXAApplication", "Set default app sound: ${selectedFile.name}")
                    }
                }
            } catch (e: Exception) {
                logError("TXAApplication", "Error initializing default app sound", e, this)
            }
            
            // Thêm tất cả nhạc chuông vào MediaStore (để xuất hiện trong danh sách hệ thống)
            soundManager.addAllAppSoundsToMediaStore()
            
            // Đăng ký nhạc chuông đã chọn (random) với hệ thống
            soundManager.registerSelectedSoundAsNotification()
        } catch (e: Exception) {
            logError("TXAApplication", "Error initializing NotificationSoundManager", e, this)
        }
        
        try {
            // Khởi động UpdateCheckService nếu có quyền battery optimization
            if (UpdateCheckService.hasBatteryOptimizationPermission(this)) {
                UpdateCheckService.startIfAllowed(this)
            }
        } catch (e: Exception) {
            logError("TXAApplication", "Error starting UpdateCheckService", e, this)
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
    
    /**
     * Setup crash handler sớm (trong attachBaseContext) khi chưa có Application context
     */
    private fun setupEarlyCrashHandler(context: Context) {
        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            try {
                // Log vào logcat ngay lập tức
                android.util.Log.e("TXAApplication", "=== EARLY CRASH DETECTED ===", exception)
                android.util.Log.e("TXAApplication", "Thread: ${thread.name}", exception)
                android.util.Log.e("TXAApplication", "Exception: ${exception.javaClass.name}", exception)
                android.util.Log.e("TXAApplication", "Message: ${exception.message}", exception)
                
                // Cố gắng ghi vào file nếu có thể
                try {
                    val logWriter = LogWriter(context)
                    val crashInfo = buildString {
                        append("=== EARLY CRASH REPORT (attachBaseContext) ===\n")
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
                    android.util.Log.e("TXAApplication", "Failed to write early crash log to file", e)
                }
            } catch (e: Exception) {
                android.util.Log.e("TXAApplication", "Failed to handle early crash", e)
            }
            
            // Gọi default handler
            defaultExceptionHandler?.uncaughtException(thread, exception)
        }
    }
    
    /**
     * Setup crash handler chính (trong onCreate) khi đã có Application context đầy đủ
     */
    private fun setupCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            try {
                // Log vào logcat ngay lập tức
                android.util.Log.e("TXAApplication", "=== CRASH DETECTED ===", exception)
                android.util.Log.e("TXAApplication", "Thread: ${thread.name}", exception)
                android.util.Log.e("TXAApplication", "Exception: ${exception.javaClass.name}", exception)
                android.util.Log.e("TXAApplication", "Message: ${exception.message}", exception)
                
                // Ghi vào file
                val logWriter = LogWriter(this)
                val appVersion = try {
                    packageManager.getPackageInfo(packageName, 0).versionName ?: "Unknown"
                } catch (e: Exception) {
                    "Unknown"
                }
                val crashInfo = buildString {
                    append("=== CRASH REPORT ===\n")
                    append("Time: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}\n")
                    append("App Version: $appVersion\n")
                    append("Android Version: ${android.os.Build.VERSION.RELEASE}\n")
                    append("Device: ${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}\n")
                    append("Thread: ${thread.name}\n")
                    append("Exception: ${exception.javaClass.name}\n")
                    append("Message: ${exception.message}\n")
                    append("\nStack Trace:\n")
                    append(exception.stackTraceToString())
                    
                    // Thêm cause nếu có
                    exception.cause?.let { cause ->
                        append("\n\nCaused by: ${cause.javaClass.name}\n")
                        append("Cause Message: ${cause.message}\n")
                        append("Cause Stack Trace:\n")
                        append(cause.stackTraceToString())
                    }
                    
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
    
    /**
     * Helper function để log lỗi vào cả logcat và file
     */
    private fun logError(tag: String, message: String, exception: Exception, context: Context) {
        try {
            // Log vào logcat
            android.util.Log.e(tag, message, exception)
            
            // Cố gắng ghi vào file
            try {
                val logWriter = LogWriter(context)
                val errorInfo = buildString {
                    append("=== ERROR LOG ===\n")
                    append("Time: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}\n")
                    append("Tag: $tag\n")
                    append("Message: $message\n")
                    append("Exception: ${exception.javaClass.name}\n")
                    append("Exception Message: ${exception.message}\n")
                    append("\nStack Trace:\n")
                    append(exception.stackTraceToString())
                    append("\n\n=== END ERROR LOG ===\n")
                }
                logWriter.writeCrashLog(errorInfo)
            } catch (e: Exception) {
                android.util.Log.e(tag, "Failed to write error log to file", e)
            }
        } catch (e: Exception) {
            android.util.Log.e(tag, "Failed to log error", e)
        }
    }
}


