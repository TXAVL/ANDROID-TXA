package com.txahub.app.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import com.txahub.app.R
import java.io.File
import java.io.FileOutputStream
import java.util.Locale

/**
 * Utility để tự động thêm key ngôn ngữ mới vào file dịch
 */
object LanguageKeySync {
    
    private const val TAG = "LanguageKeySync"
    
    /**
     * Đồng bộ keys từ file tiếng Việt (values/strings.xml) sang file tiếng Anh (values-en/strings.xml)
     * Tự động thêm các key mới vào file tiếng Anh nếu chưa có
     */
    fun syncLanguageKeys(context: Context) {
        try {
            // Đọc tất cả keys từ file tiếng Việt (default locale)
            val viKeys = getAllStringKeys(context, Locale("vi", "VN"))
            
            // Đọc tất cả keys từ file tiếng Anh
            val enKeys = getAllStringKeys(context, Locale("en", "US"))
            
            // Tìm keys có trong tiếng Việt nhưng chưa có trong tiếng Anh
            val missingKeys = viKeys.keys.filter { key ->
                !enKeys.containsKey(key)
            }
            
            if (missingKeys.isEmpty()) {
                Log.d(TAG, "All language keys are synced")
                return
            }
            
            Log.w(TAG, "Found ${missingKeys.size} missing keys in English translation")
            
            // Log và tạo file chứa các key còn thiếu
            logMissingKeys(context, missingKeys, viKeys)
            generateMissingKeysFile(context, missingKeys, viKeys)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing language keys", e)
        }
    }
    
    /**
     * Lấy tất cả string keys từ resources bằng cách dùng reflection trên R.string
     */
    private fun getAllStringKeys(context: Context, locale: Locale): Map<String, String> {
        val keys = mutableMapOf<String, String>()
        
        try {
            // Tạo configuration với locale cụ thể
            val config = Configuration(context.resources.configuration)
            config.setLocale(locale)
            val localizedContext = context.createConfigurationContext(config)
            val resources = localizedContext.resources
            
            // Lấy tất cả fields từ R.string class
            val stringClass = R.string::class.java
            val fields = stringClass.fields
            
            for (field in fields) {
                try {
                    val resourceId = field.getInt(null)
                    val resourceName = field.name
                    
                    // Lấy giá trị string
                    val stringValue = try {
                        resources.getString(resourceId)
                    } catch (e: Resources.NotFoundException) {
                        // Key không tồn tại trong locale này
                        continue
                    }
                    
                    keys[resourceName] = stringValue
                } catch (e: Exception) {
                    // Bỏ qua field không phải là int constant
                    continue
                }
            }
            
            Log.d(TAG, "Found ${keys.size} string keys for locale: ${locale.language}")
            
        } catch (e: Exception) {
            Log.e(TAG, "Error reading string keys for locale: ${locale.language}", e)
        }
        
        return keys
    }
    
    /**
     * Log các key còn thiếu để developer có thể thêm thủ công
     */
    private fun logMissingKeys(context: Context, missingKeys: List<String>, viKeys: Map<String, String>) {
        val logWriter = LogWriter(context)
        val missingKeysInfo = buildString {
            append("=== MISSING LANGUAGE KEYS ===\n")
            append("Time: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}\n")
            append("Total missing keys: ${missingKeys.size}\n\n")
            append("Add these keys to values-en/strings.xml:\n\n")
            
            missingKeys.forEach { key ->
                val viValue = viKeys[key] ?: ""
                // Escape XML
                val escapedValue = viValue
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "\\'")
                
                append("    <string name=\"$key\">$escapedValue</string>\n")
            }
            
            append("\n=== END MISSING KEYS ===\n")
        }
        
        // Log vào logcat
        Log.w(TAG, missingKeysInfo)
        
        // Ghi vào file log
        logWriter.writeAppLog(
            missingKeysInfo,
            TAG,
            Log.WARN
        )
    }
    
    /**
     * Tạo file XML mẫu chứa các key còn thiếu để developer có thể copy vào values-en/strings.xml
     * File sẽ được lưu trong external storage
     */
    private fun generateMissingKeysFile(context: Context, missingKeys: List<String>, viKeys: Map<String, String>) {
        try {
            if (missingKeys.isEmpty()) {
                return
            }
            
            // Tạo file XML
            val xmlContent = buildString {
                append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
                append("<resources>\n\n")
                append("    <!-- Missing keys - Copy these to values-en/strings.xml -->\n")
                append("    <!-- Total: ${missingKeys.size} keys -->\n\n")
                
                missingKeys.forEach { key ->
                    val viValue = viKeys[key] ?: ""
                    // Escape XML
                    val escapedValue = viValue
                        .replace("&", "&amp;")
                        .replace("<", "&lt;")
                        .replace(">", "&gt;")
                        .replace("\"", "&quot;")
                        .replace("'", "\\'")
                    
                    append("    <string name=\"$key\">$escapedValue</string>\n")
                }
                
                append("\n</resources>\n")
            }
            
            // Lưu file vào external storage
            val baseDir = context.getExternalFilesDir(null) ?: context.filesDir
            val outputFile = File(baseDir, "missing_language_keys.xml")
            
            FileOutputStream(outputFile).use { outputStream ->
                outputStream.write(xmlContent.toByteArray(Charsets.UTF_8))
            }
            
            Log.d(TAG, "Generated missing keys file: ${outputFile.absolutePath}")
            
            // Ghi log
            val logWriter = LogWriter(context)
            logWriter.writeAppLog(
                "Generated missing language keys file: ${outputFile.absolutePath}\nMissing keys: ${missingKeys.size}",
                TAG,
                Log.INFO
            )
            
        } catch (e: Exception) {
            Log.e(TAG, "Error generating missing keys file", e)
        }
    }
}
