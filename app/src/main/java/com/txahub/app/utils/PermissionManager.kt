package com.txahub.app.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.core.content.ContextCompat

data class PermissionInfo(
    val permission: String,
    val name: String,
    val description: String,
    val isGranted: Boolean,
    val canRequest: Boolean,
    val settingsIntent: Intent?
)

class PermissionManager(private val context: Context) {
    
    companion object {
        // Các quyền cần thiết cho app
        val REQUIRED_PERMISSIONS = mutableListOf<String>().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.POST_NOTIFICATIONS)
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }
    
    /**
     * Kiểm tra quyền thông báo
     */
    fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            // Android < 13: Không cần runtime permission
            true
        }
    }
    
    /**
     * Kiểm tra quyền ghi file
     * Android 11+: Cần MANAGE_EXTERNAL_STORAGE (để ghi vào Downloads/TXAAPP)
     * Android < 11: Cần WRITE_EXTERNAL_STORAGE
     */
    fun hasStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+: Kiểm tra MANAGE_EXTERNAL_STORAGE
            try {
                android.os.Environment.isExternalStorageManager()
            } catch (e: Exception) {
                false
            }
        } else {
            // Android < 11: Cần WRITE_EXTERNAL_STORAGE
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    /**
     * Kiểm tra quyền bỏ qua tối ưu hóa pin
     */
    fun hasBatteryOptimizationPermission(): Boolean {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pm.isIgnoringBatteryOptimizations(context.packageName)
        } else {
            true
        }
    }
    
    /**
     * Lấy Intent để mở cài đặt bỏ qua tối ưu hóa pin
     */
    fun getBatteryOptimizationSettingsIntent(): Intent {
        return Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = Uri.parse("package:${context.packageName}")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
    
    /**
     * Lấy Intent để mở cài đặt thông báo
     */
    fun getNotificationSettingsIntent(): Intent {
        return Intent().apply {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra("app_package", context.packageName)
                    putExtra("app_uid", context.applicationInfo.uid)
                }
                else -> {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.parse("package:${context.packageName}")
                }
            }
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
    
    /**
     * Lấy Intent để mở cài đặt lưu trữ
     */
    fun getStorageSettingsIntent(): Intent {
        return Intent().apply {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    // Android 11+: Mở cài đặt MANAGE_EXTERNAL_STORAGE
                    try {
                        action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                        data = Uri.parse("package:${context.packageName}")
                    } catch (e: Exception) {
                        // Fallback nếu không hỗ trợ
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = Uri.parse("package:${context.packageName}")
                    }
                }
                else -> {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.parse("package:${context.packageName}")
                }
            }
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
    
    /**
     * Lấy danh sách tất cả quyền cần kiểm tra
     */
    fun getAllPermissionInfos(): List<PermissionInfo> {
        val permissions = mutableListOf<PermissionInfo>()
        
        // Quyền thông báo
        val hasNotification = hasNotificationPermission()
        permissions.add(
            PermissionInfo(
                permission = Manifest.permission.POST_NOTIFICATIONS,
                name = "Thông báo",
                description = "Cho phép app gửi thông báo về bản cập nhật mới",
                isGranted = hasNotification,
                canRequest = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotification,
                settingsIntent = if (!hasNotification) getNotificationSettingsIntent() else null
            )
        )
        
        // Quyền lưu trữ
        val hasStorage = hasStoragePermission()
        val storagePermissionName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
        } else {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        }
        permissions.add(
            PermissionInfo(
                permission = storagePermissionName,
                name = "Lưu trữ",
                description = "Cho phép app lưu file log và nhạc chuông",
                isGranted = hasStorage,
                canRequest = !hasStorage,
                settingsIntent = if (!hasStorage) getStorageSettingsIntent() else null
            )
        )
        
        // Quyền bỏ qua tối ưu hóa pin
        val hasBatteryOptimization = hasBatteryOptimizationPermission()
        permissions.add(
            PermissionInfo(
                permission = "battery_optimization",
                name = "Bỏ qua tối ưu hóa pin",
                description = "Cho phép app chạy nền để kiểm tra cập nhật và gửi thông báo",
                isGranted = hasBatteryOptimization,
                canRequest = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasBatteryOptimization,
                settingsIntent = if (!hasBatteryOptimization) getBatteryOptimizationSettingsIntent() else null
            )
        )
        
        return permissions
    }
    
    /**
     * Kiểm tra xem tất cả quyền đã được cấp chưa
     */
    fun areAllPermissionsGranted(): Boolean {
        return getAllPermissionInfos().all { it.isGranted }
    }
    
    /**
     * Lấy danh sách quyền chưa được cấp
     */
    fun getMissingPermissions(): List<PermissionInfo> {
        return getAllPermissionInfos().filter { !it.isGranted }
    }
}

