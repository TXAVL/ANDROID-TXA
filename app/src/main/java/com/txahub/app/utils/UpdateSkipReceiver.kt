package com.txahub.app.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat

/**
 * BroadcastReceiver để xử lý action "Bỏ qua" trong notification cập nhật
 */
class UpdateSkipReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == NotificationHelper.ACTION_SKIP_UPDATE) {
            val versionName = intent.getStringExtra(NotificationHelper.EXTRA_VERSION_NAME)
            
            if (versionName != null) {
                // Lưu version đã bỏ qua vào SharedPreferences
                val prefs = context.getSharedPreferences("txahub_prefs", Context.MODE_PRIVATE)
                val skippedVersions = prefs.getStringSet("skipped_versions", mutableSetOf()) ?: mutableSetOf()
                skippedVersions.add(versionName)
                prefs.edit()
                    .putStringSet("skipped_versions", skippedVersions)
                    .apply()
                
                // Đóng notification
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.cancel(NotificationHelper.NOTIFICATION_ID_UPDATE)
                
                Log.d("UpdateSkipReceiver", "Đã bỏ qua version: $versionName")
                
                // Ghi log
                val logWriter = LogWriter(context)
                logWriter.writeAppLog(
                    "User skipped update for version: $versionName",
                    "UpdateSkipReceiver",
                    Log.INFO
                )
            }
        }
    }
}

