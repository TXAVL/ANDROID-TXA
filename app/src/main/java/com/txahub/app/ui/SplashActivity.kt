package com.txahub.app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.txahub.app.R
import com.txahub.app.data.local.PreferencesManager
import com.txahub.app.data.api.ApiClient
import com.txahub.app.utils.PermissionManager
import com.txahub.app.utils.PermissionRequestDialog
import com.txahub.app.utils.UpdateChecker
import com.txahub.app.utils.ChangelogActivity
import com.txahub.app.ui.auth.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var permissionManager: PermissionManager
    private var hasShownChangelog = false
    private var hasShownPermissions = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            setContentView(R.layout.activity_splash)
            
            preferencesManager = PreferencesManager(this)
            permissionManager = PermissionManager(this)
            
            // Xử lý deeplink nếu có
            val handledDeepLink = handleDeepLink(intent)
            
            // Nếu đã xử lý deeplink, không cần chạy các bước tiếp theo
            if (!handledDeepLink) {
                // Kiểm tra quyền trước
                checkPermissionsAndProceed()
            }
        } catch (e: Exception) {
            android.util.Log.e("SplashActivity", "Error in onCreate", e)
            // Nếu có lỗi, vẫn cố gắng chuyển đến màn hình tiếp theo
            try {
                val prefsManager = try {
                    PreferencesManager(this)
                } catch (e2: Exception) {
                    android.util.Log.e("SplashActivity", "Error creating PreferencesManager", e2)
                    null
                }
                
                CoroutineScope(Dispatchers.Main).launch {
                    val token = prefsManager?.getAuthTokenSync()
                    if (!token.isNullOrEmpty()) {
                        ApiClient.setAuthToken(token)
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    }
                    finish()
                }
            } catch (e2: Exception) {
                android.util.Log.e("SplashActivity", "Error in fallback navigation", e2)
                // Cuối cùng, nếu vẫn lỗi, chuyển đến LoginActivity
                try {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } catch (e3: Exception) {
                    android.util.Log.e("SplashActivity", "Critical error - cannot navigate", e3)
                }
            }
        }
    }
    
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        // Xử lý deeplink khi app đã mở
        intent?.let { handleDeepLink(it) }
    }
    
    private fun handleDeepLink(intent: Intent?): Boolean {
        val data = intent?.data
        if (data != null && data.scheme == "txahub") {
            val path = data.host ?: ""
            when (path) {
                "update" -> {
                    // Mở Settings và scroll đến phần update
                    val settingsIntent = Intent(this, SettingsActivity::class.java).apply {
                        putExtra("scroll_to", "update")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    startActivity(settingsIntent)
                    finish()
                    return true
                }
                "settings" -> {
                    // Mở Settings
                    val settingsIntent = Intent(this, SettingsActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    startActivity(settingsIntent)
                    finish()
                    return true
                }
                "logs" -> {
                    // Mở LogViewerActivity
                    val logsIntent = Intent(this, LogViewerActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    startActivity(logsIntent)
                    finish()
                    return true
                }
                "changelog" -> {
                    // Mở ChangelogActivity
                    val changelogIntent = Intent(this, ChangelogActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    startActivity(changelogIntent)
                    finish()
                    return true
                }
                "dashboard" -> {
                    // Mở MainActivity (Dashboard)
                    CoroutineScope(Dispatchers.Main).launch {
                        val token = preferencesManager.getAuthTokenSync()
                        if (!token.isNullOrEmpty()) {
                            ApiClient.setAuthToken(token)
                            val dashboardIntent = Intent(this@SplashActivity, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            }
                            startActivity(dashboardIntent)
                            finish()
                        } else {
                            // Chưa đăng nhập, chuyển đến LoginActivity
                            val loginIntent = Intent(this@SplashActivity, LoginActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            }
                            startActivity(loginIntent)
                            finish()
                        }
                    }
                    return true
                }
            }
        }
        return false
    }
    
    private fun checkPermissionsAndProceed() {
        val missingPermissions = permissionManager.getMissingPermissions()
        
        if (missingPermissions.isNotEmpty() && !hasShownPermissions) {
            // Hiển thị dialog yêu cầu quyền
            hasShownPermissions = true
            permissionDialog = PermissionRequestDialog(this)
            permissionDialog?.show { allGranted ->
                // Chỉ tiếp tục nếu TẤT CẢ quyền đã được cấp
                if (allGranted) {
                    permissionDialog = null
                    checkChangelogAndProceed()
                } else {
                    // Chưa cấp đủ quyền, kiểm tra lại sau khi quay lại từ Settings
                    // (onResume sẽ gọi refreshPermissionList)
                }
            }
        } else if (missingPermissions.isEmpty()) {
            // Đã có đủ quyền, kiểm tra changelog
            checkChangelogAndProceed()
        } else {
            // Đã hiển thị dialog nhưng vẫn còn quyền chưa cấp
            // Không làm gì, chờ user quay lại từ Settings (onResume sẽ xử lý)
        }
    }
    
    private fun checkChangelogAndProceed() {
        // Kiểm tra xem đã hiển thị changelog chưa (chỉ hiển thị lần đầu mỗi lần khởi động)
        val prefs = getSharedPreferences("txahub_prefs", MODE_PRIVATE)
        val lastShownVersion = prefs.getString("last_changelog_version", "")
        val currentVersion = getCurrentVersion()
        
        if (lastShownVersion != currentVersion && !hasShownChangelog) {
            // Hiển thị changelog
            hasShownChangelog = true
            val updateChecker = UpdateChecker(this)
            updateChecker.checkUpdate { updateInfo ->
                runOnUiThread {
                    if (updateInfo != null) {
                        // Có bản cập nhật, hiển thị changelog
                        val intent = Intent(this, ChangelogActivity::class.java).apply {
                            putExtra(ChangelogActivity.EXTRA_VERSION_NAME, updateInfo.versionName)
                            putExtra(ChangelogActivity.EXTRA_CHANGELOG, updateInfo.changelog)
                        }
                        startActivity(intent)
                        
                        // Lưu version đã hiển thị
                        prefs.edit().putString("last_changelog_version", currentVersion).apply()
                        
                        // Chờ một chút rồi chuyển màn hình
                        Handler(Looper.getMainLooper()).postDelayed({
                            proceedToNextScreen()
                        }, 2000)
                    } else {
                        // Không có bản cập nhật, chuyển màn hình luôn
                        proceedToNextScreen()
                    }
                }
            }
        } else {
            // Đã hiển thị changelog hoặc không cần, chuyển màn hình
            proceedToNextScreen()
        }
    }
    
    private fun proceedToNextScreen() {
        // Kiểm tra đăng nhập
        CoroutineScope(Dispatchers.Main).launch {
            val token = preferencesManager.getAuthTokenSync()
            if (!token.isNullOrEmpty()) {
                // Đã có token, set vào ApiClient và chuyển đến MainActivity
                ApiClient.setAuthToken(token)
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }, 1000)
            } else {
                // Chưa đăng nhập, chuyển đến LoginActivity
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()
                }, 1000)
            }
        }
    }
    
    private fun getCurrentVersion(): String {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionName ?: "1.0.0_txa"
        } catch (e: Exception) {
            "1.0.0_txa"
        }
    }
    
    private var permissionDialog: PermissionRequestDialog? = null
    
    override fun onResume() {
        super.onResume()
        // Khi quay lại từ cài đặt, refresh dialog nếu đang hiển thị
        permissionDialog?.refreshPermissionList()
        
        // Kiểm tra lại quyền
        if (hasShownPermissions) {
            hasShownPermissions = false // Reset để có thể hiển thị dialog lại nếu vẫn còn quyền chưa cấp
            checkPermissionsAndProceed()
        }
    }
}



