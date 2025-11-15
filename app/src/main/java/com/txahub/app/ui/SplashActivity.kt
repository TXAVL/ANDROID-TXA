package com.txahub.app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
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
    private var splashTimeoutHandler: Handler? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            setContentView(R.layout.activity_splash)
            
            // Khởi tạo managers trước
            preferencesManager = PreferencesManager(this)
            permissionManager = PermissionManager(this)
            
            // Chạy animation splash screen
            startSplashAnimation()
            
            // Hiển thị toast "©️POWER BY TXA" sau một chút delay
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "©️POWER BY TXA", Toast.LENGTH_SHORT).show()
            }, 1500) // Hiển thị sau 1.5 giây
            
            // Xử lý deeplink nếu có (sau khi đã khởi tạo managers)
            val handledDeepLink = handleDeepLink(intent)
            
            // Nếu đã xử lý deeplink, không cần chạy các bước tiếp theo
            if (!handledDeepLink) {
                // Kiểm tra quyền sau khi animation bắt đầu (delay ngắn hơn)
                Handler(Looper.getMainLooper()).postDelayed({
                    checkPermissionsAndProceed()
                }, 800) // Đợi 800ms để animation logo bắt đầu
                
                // Timeout: Nếu sau 10 giây vẫn chưa chuyển màn hình, force chuyển
                splashTimeoutHandler = Handler(Looper.getMainLooper())
                splashTimeoutHandler?.postDelayed({
                    android.util.Log.w("SplashActivity", "Splash timeout, forcing navigation")
                    proceedToNextScreen()
                }, 10000) // 10 giây timeout
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
    
    private fun startSplashAnimation() {
        val ivLogo = findViewById<android.widget.ImageView>(R.id.ivLogo)
        val tvAppName = findViewById<android.widget.TextView>(R.id.tvAppName)
        val progressBar = findViewById<android.widget.ProgressBar>(R.id.progressBar)
        
        // Ẩn ban đầu
        ivLogo.alpha = 0f
        tvAppName.alpha = 0f
        
        // Chạy animation cho logo
        val logoAnimation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.splash_logo_animation)
        ivLogo.startAnimation(logoAnimation)
        ivLogo.animate().alpha(1f).setDuration(800).start()
        
        // Chạy animation cho text sau 300ms
        Handler(Looper.getMainLooper()).postDelayed({
            val textAnimation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.splash_text_animation)
            tvAppName.startAnimation(textAnimation)
            tvAppName.animate().alpha(1f).setDuration(600).start()
        }, 300)
        
        // Hiển thị progress bar sau khi animation xong
        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = android.view.View.VISIBLE
        }, 1200)
    }
    
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        // Xử lý deeplink khi app đã mở
        intent?.let { handleDeepLink(it) }
    }
    
    private fun handleDeepLink(intent: Intent?): Boolean {
        try {
            val data = intent?.data
            if (data != null && data.scheme == "txahub") {
                val path = data.host ?: ""
                android.util.Log.d("SplashActivity", "Handling deeplink: $path")
                
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
                        // Đảm bảo preferencesManager đã được khởi tạo
                        if (!::preferencesManager.isInitialized) {
                            preferencesManager = PreferencesManager(this)
                        }
                        
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
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
                            } catch (e: Exception) {
                                android.util.Log.e("SplashActivity", "Error handling dashboard deeplink", e)
                                // Fallback: chuyển đến LoginActivity
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
        } catch (e: Exception) {
            android.util.Log.e("SplashActivity", "Error handling deeplink", e)
        }
        return false
    }
    
    private fun checkPermissionsAndProceed() {
        // Luôn hiển thị dialog quyền (không check missingPermissions trước)
        if (!hasShownPermissions) {
            hasShownPermissions = true
            permissionDialog = PermissionRequestDialog(this)
            permissionDialog?.show { allGranted ->
                // Chỉ tiếp tục nếu TẤT CẢ quyền đã được cấp
                if (allGranted) {
                    permissionDialog = null
                    splashTimeoutHandler?.removeCallbacksAndMessages(null)
                    checkChangelogAndProceed()
                }
                // Nếu chưa cấp đủ quyền, dialog vẫn hiển thị và chờ user cấp
            }
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
            
            // Timeout cho việc check update: nếu quá 3 giây thì bỏ qua
            val updateCheckTimeout = Handler(Looper.getMainLooper())
            updateCheckTimeout.postDelayed({
                android.util.Log.w("SplashActivity", "Update check timeout, proceeding")
                proceedToNextScreen()
            }, 3000)
            
            updateChecker.checkUpdate { updateInfo ->
                updateCheckTimeout.removeCallbacksAndMessages(null)
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
        // Hủy timeout nếu đã được set
        splashTimeoutHandler?.removeCallbacksAndMessages(null)
        
        // Kiểm tra đăng nhập
        CoroutineScope(Dispatchers.Main).launch {
            val token = preferencesManager.getAuthTokenSync()
            if (!token.isNullOrEmpty()) {
                // Đã có token, set vào ApiClient và chuyển đến MainActivity
                ApiClient.setAuthToken(token)
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }, 500) // Giảm delay xuống 500ms
            } else {
                // Chưa đăng nhập, chuyển đến LoginActivity
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()
                }, 500) // Giảm delay xuống 500ms
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
        // Điều này sẽ cập nhật trạng thái các quyền và enable nút Đóng nếu tất cả đã cấp
        permissionDialog?.refreshPermissionList()
    }
}



