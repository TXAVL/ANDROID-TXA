package com.txahub.app.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

/**
 * Quản lý Text-to-Speech để đọc thông báo
 */
class NotificationTTSManager(private val context: Context) {
    
    companion object {
        private const val PREFS_NAME = "txahub_tts_prefs"
        private const val KEY_TTS_ENABLED = "tts_enabled"
        private const val KEY_TTS_LANGUAGE = "tts_language"
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private var tts: TextToSpeech? = null
    private var isInitialized = false
    
    /**
     * Khởi tạo TTS
     */
    fun initialize(callback: (Boolean) -> Unit) {
        if (tts != null && isInitialized) {
            Log.d("NotificationTTSManager", "TTS already initialized")
            callback(true)
            return
        }
        
        Log.d("NotificationTTSManager", "Starting TTS initialization...")
        tts = TextToSpeech(context) { status ->
            Log.d("NotificationTTSManager", "TTS initialization callback: status=$status")
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale("vi", "VN")) ?: TextToSpeech.LANG_MISSING_DATA
                Log.d("NotificationTTSManager", "TTS language set result: $result")
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Fallback về tiếng Anh nếu không hỗ trợ tiếng Việt
                    Log.w("NotificationTTSManager", "Vietnamese not supported, falling back to English")
                    tts?.setLanguage(Locale.US)
                }
                isInitialized = true
                Log.d("NotificationTTSManager", "TTS initialized successfully")
                callback(true)
            } else {
                Log.e("NotificationTTSManager", "TTS initialization failed with status: $status")
                callback(false)
            }
        }
    }
    
    /**
     * Kiểm tra xem TTS có được bật không
     */
    fun isTTSEnabled(): Boolean {
        return prefs.getBoolean(KEY_TTS_ENABLED, false)
    }
    
    /**
     * Bật/tắt TTS
     */
    fun setTTSEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_TTS_ENABLED, enabled).apply()
        if (!enabled && tts != null) {
            tts?.stop()
        } else if (enabled && tts == null) {
            // Nếu bật TTS, khởi tạo ngay
            initialize { }
        }
    }
    
    /**
     * Đọc văn bản
     */
    fun speak(text: String, utteranceId: String = "notification_${System.currentTimeMillis()}") {
        if (!isTTSEnabled() || !isInitialized) {
            return
        }
        
        tts?.let { textToSpeech ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
            } else {
                @Suppress("DEPRECATION")
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
            }
        }
    }
    
    /**
     * Đọc thông báo với câu cuối cùng "APP ĐƯỢC BUILD BỞI TÊ ÍCH A"
     */
    fun speakNotification(text: String, utteranceId: String = "notification_${System.currentTimeMillis()}") {
        Log.d("NotificationTTSManager", "speakNotification called: enabled=${isTTSEnabled()}, initialized=$isInitialized")
        
        if (!isTTSEnabled()) {
            Log.w("NotificationTTSManager", "TTS is disabled, cannot speak")
            return
        }
        
        if (!isInitialized) {
            Log.w("NotificationTTSManager", "TTS not initialized yet, initializing now...")
            initialize { success ->
                if (success && isAvailable()) {
                    val finalText = "$text. APP ĐƯỢC BUILD BỞI TÊ ÍCH A"
                    Log.d("NotificationTTSManager", "Speaking after initialization: $finalText")
                    tts?.let { textToSpeech ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            val result = textToSpeech.speak(finalText, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
                            Log.d("NotificationTTSManager", "TTS speak result: $result")
                        } else {
                            @Suppress("DEPRECATION")
                            textToSpeech.speak(finalText, TextToSpeech.QUEUE_FLUSH, null)
                        }
                    }
                } else {
                    Log.e("NotificationTTSManager", "TTS initialization failed in speakNotification")
                }
            }
            return
        }
        
        tts?.let { textToSpeech ->
            val finalText = "$text. APP ĐƯỢC BUILD BỞI TÊ ÍCH A"
            Log.d("NotificationTTSManager", "Speaking: $finalText")
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val result = textToSpeech.speak(finalText, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
                Log.d("NotificationTTSManager", "TTS speak result: $result")
                if (result == TextToSpeech.ERROR) {
                    Log.e("NotificationTTSManager", "TTS speak returned ERROR")
                }
            } else {
                @Suppress("DEPRECATION")
                textToSpeech.speak(finalText, TextToSpeech.QUEUE_FLUSH, null)
            }
        } ?: run {
            Log.e("NotificationTTSManager", "TTS instance is null, cannot speak")
        }
    }
    
    /**
     * Dừng đọc
     */
    fun stop() {
        tts?.stop()
    }
    
    /**
     * Giải phóng tài nguyên
     */
    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isInitialized = false
    }
    
    /**
     * Kiểm tra xem TTS có sẵn không
     */
    fun isAvailable(): Boolean {
        return isInitialized && tts != null
    }
}



