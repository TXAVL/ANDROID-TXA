package com.txahub.app.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.Locale

object LocaleHelper {
    
    const val LANGUAGE_AUTO = "auto"
    const val LANGUAGE_VI = "vi"
    const val LANGUAGE_EN = "en"
    
    fun setLocale(context: Context, language: String): Context {
        val locale = when (language) {
            LANGUAGE_AUTO -> {
                // Auto detect từ hệ thống
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    context.resources.configuration.locales[0]
                } else {
                    @Suppress("DEPRECATION")
                    context.resources.configuration.locale
                }
            }
            LANGUAGE_VI -> Locale("vi", "VN")
            LANGUAGE_EN -> Locale("en", "US")
            else -> Locale.getDefault()
        }
        
        return updateResources(context, locale)
    }
    
    private fun updateResources(context: Context, locale: Locale): Context {
        val res: Resources = context.resources
        val config: Configuration = res.configuration
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            return context.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
            @Suppress("DEPRECATION")
            res.updateConfiguration(config, res.displayMetrics)
            return context
        }
    }
    
    fun getLanguageCode(context: Context): String {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }
        return locale.language
    }
}



