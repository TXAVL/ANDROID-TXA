package com.txahub.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.txahub.app.R
import com.txahub.app.databinding.ActivityMainBinding
import com.txahub.app.data.local.PreferencesManager
import com.txahub.app.data.repository.AuthRepository
import com.txahub.app.ui.admin.AdminManagementActivity
import com.txahub.app.ui.auth.LoginActivity
import com.txahub.app.ui.SettingsActivity
import com.txahub.app.ui.LogViewerActivity
import com.txahub.app.utils.ChangelogActivity
import com.txahub.app.utils.ChangelogDialog
import com.txahub.app.utils.UpdateChecker
import com.txahub.app.utils.UpdateInfo
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var updateChecker: UpdateChecker
    private var isAdmin = false
    private var isLoggedIn = false
    private var hasCheckedUpdate = false // Để tránh check nhiều lần trong cùng một session
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        preferencesManager = PreferencesManager(this)
        authRepository = AuthRepository(preferencesManager)
        updateChecker = UpdateChecker(this)
        
        setupUI()
        setupNavigationDrawer()
        loadDashboard()
        checkAndShowChangelog()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        
        binding.fabAdmin.setOnClickListener {
            if (isAdmin) {
                startActivity(Intent(this, AdminManagementActivity::class.java))
            } else {
                Toast.makeText(this, getString(R.string.txa_global_error_no_access), Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun setupNavigationDrawer() {
        lifecycleScope.launch {
            val token = preferencesManager.getAuthTokenSync()
            isLoggedIn = !token.isNullOrEmpty()
            
            // Load menu tương ứng với login status
            if (isLoggedIn) {
                // Đã login: dùng menu đầy đủ
                binding.navigationView.menu.clear()
                binding.navigationView.inflateMenu(R.menu.nav_drawer_menu)
            } else {
                // Chưa login: dùng menu guest (căn bản)
                binding.navigationView.menu.clear()
                binding.navigationView.inflateMenu(R.menu.nav_drawer_menu_guest)
            }
            
            // Setup drawer toggle
            drawerToggle = ActionBarDrawerToggle(
                this@MainActivity,
                binding.drawerLayout,
                binding.toolbar,
                R.string.txa_global_menu_dashboard,
                R.string.txa_global_menu_dashboard
            )
            
            // Nếu chưa login: disable hamburger icon, chỉ cho swipe từ trái
            if (!isLoggedIn) {
                drawerToggle.isDrawerIndicatorEnabled = false
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setHomeButtonEnabled(false)
            } else {
                // Đã login: enable hamburger icon
                drawerToggle.isDrawerIndicatorEnabled = true
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeButtonEnabled(true)
            }
            
            binding.drawerLayout.addDrawerListener(drawerToggle)
            drawerToggle.syncState()
            
            binding.navigationView.setNavigationItemSelectedListener { menuItem ->
                handleNavigationItemSelected(menuItem)
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            
            // Set dashboard as selected by default
            binding.navigationView.setCheckedItem(R.id.nav_dashboard)
            
            // Nếu chưa login và có item logout, ẩn nó
            if (!isLoggedIn) {
                binding.navigationView.menu.findItem(R.id.nav_admin)?.isVisible = isAdmin
            }
        }
    }
    
    private fun handleNavigationItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.nav_dashboard -> {
                true
            }
            R.id.nav_logs -> {
                if (isLoggedIn) {
                    startActivity(Intent(this, LogViewerActivity::class.java))
                } else {
                    // Chưa login: chuyển đến LoginActivity
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                true
            }
            R.id.nav_changelog -> {
                startActivity(Intent(this, ChangelogActivity::class.java))
                true
            }
            R.id.nav_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.nav_admin -> {
                if (isLoggedIn && isAdmin) {
                    startActivity(Intent(this, AdminManagementActivity::class.java))
                } else {
                    Toast.makeText(this, getString(R.string.txa_global_error_no_access), Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.nav_logout -> {
                if (isLoggedIn) {
                    showLogoutDialog()
                } else {
                    // Chưa login: chuyển đến LoginActivity
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                true
            }
            else -> false
        }
    }
    
    private fun checkAdminStatus() {
        lifecycleScope.launch {
            if (isLoggedIn) {
                isAdmin = authRepository.isAdmin()
                binding.fabAdmin.visibility = if (isAdmin) {
                    android.view.View.VISIBLE
                } else {
                    android.view.View.GONE
                }
                // Show/hide admin menu item in drawer
                binding.navigationView.menu.findItem(R.id.nav_admin)?.isVisible = isAdmin
                invalidateOptionsMenu()
            }
        }
    }
    
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    
    private fun loadDashboard() {
        lifecycleScope.launch {
            binding.progressBar.visibility = android.view.View.VISIBLE
            
            val statisticsRepository = com.txahub.app.data.repository.StatisticsRepository()
            val result = statisticsRepository.getUserStatistics()
            
            binding.progressBar.visibility = android.view.View.GONE
            
            result.fold(
                onSuccess = { stats ->
                    binding.tvTotalClicks.text = stats.totalClicks.toString()
                    binding.tvTotalLinks.text = stats.totalLinks.toString()
                    binding.tvTotalProjects.text = stats.totalProjects.toString()
                    binding.tvClicksToday.text = getString(R.string.txa_global_clicks_today_format, getString(R.string.txa_global_clicks_today), stats.clicksToday)
                    binding.tvClicksWeek.text = getString(R.string.txa_global_clicks_week_format, getString(R.string.txa_global_clicks_week), stats.clicksThisWeek)
                    binding.tvClicksMonth.text = getString(R.string.txa_global_clicks_month_format, getString(R.string.txa_global_clicks_month), stats.clicksThisMonth)
                },
                onFailure = { error ->
                    Toast.makeText(
                        this@MainActivity,
                        error.message ?: getString(R.string.txa_global_error_statistics_failed),
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.menu_admin).isVisible = isAdmin
        menu.findItem(R.id.menu_settings).isVisible = true
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.menu_logout -> {
                showLogoutDialog()
                true
            }
            R.id.menu_admin -> {
                if (isAdmin) {
                    startActivity(Intent(this, AdminManagementActivity::class.java))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.txa_global_logout_title))
            .setMessage(getString(R.string.txa_global_logout_confirm))
            .setPositiveButton(getString(R.string.txa_global_logout)) { _, _ ->
                lifecycleScope.launch {
                    authRepository.logout()
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
            }
            .setNegativeButton(getString(R.string.txa_global_cancel), null)
            .show()
    }
    
    override fun onResume() {
        super.onResume()
        // Kiểm tra lại login status khi resume
        lifecycleScope.launch {
            val token = preferencesManager.getAuthTokenSync()
            val wasLoggedIn = isLoggedIn
            isLoggedIn = !token.isNullOrEmpty()
            
            // Nếu login status thay đổi, setup lại drawer
            if (wasLoggedIn != isLoggedIn) {
                setupNavigationDrawer()
                if (isLoggedIn) {
                    checkAdminStatus()
                }
            }
        }
        loadDashboard()
        
        // Kiểm tra cập nhật khi mở app (chỉ check một lần mỗi session)
        if (!hasCheckedUpdate) {
            hasCheckedUpdate = true
            checkUpdateOnResume()
        }
    }
    
    /**
     * Kiểm tra cập nhật khi mở app và hiển thị modal nếu có bản mới
     */
    private fun checkUpdateOnResume() {
        updateChecker.checkUpdate { updateInfo ->
            runOnUiThread {
                if (updateInfo != null) {
                    // Có bản cập nhật, hiển thị modal
                    showUpdateDialog(updateInfo)
                }
            }
        }
    }
    
    /**
     * Hiển thị dialog cập nhật
     * Nếu forceUpdate = true: chỉ có nút "Cập nhật", không thể đóng
     * Nếu forceUpdate = false: có nút "Cập nhật" và "Bỏ qua"
     */
    private fun showUpdateDialog(updateInfo: UpdateInfo) {
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle(
                if (updateInfo.forceUpdate) {
                    "Cập nhật bắt buộc - TXA Hub"
                } else {
                    "Có bản cập nhật mới - TXA Hub"
                }
            )
            .setMessage(
                "Phiên bản ${updateInfo.versionName} đã có sẵn.\n\n" +
                if (updateInfo.changelog.isNotBlank()) {
                    "Thay đổi:\n${updateInfo.changelog}"
                } else {
                    "Vui lòng cập nhật để có trải nghiệm tốt nhất."
                }
            )
            .setPositiveButton("Cập nhật") { _, _ ->
                // Mở trình duyệt để tải
                val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(updateInfo.downloadUrl))
                startActivity(intent)
            }
        
        // Nếu không phải force update, thêm nút "Bỏ qua"
        if (!updateInfo.forceUpdate) {
            dialogBuilder.setNegativeButton("Bỏ qua", null)
        }
        
        val dialog = dialogBuilder.create()
        
        // Nếu là force update, không cho phép đóng bằng nút back
        if (updateInfo.forceUpdate) {
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
        }
        
        dialog.show()
    }
    
    /**
     * Kiểm tra và hiển thị changelog cho version hiện tại nếu chưa xem
     */
    private fun checkAndShowChangelog() {
        lifecycleScope.launch {
            val currentVersion = updateChecker.getCurrentVersion()
            val hasViewed = preferencesManager.hasViewedChangelogForVersion(currentVersion)
            
            if (!hasViewed) {
                // Chưa xem changelog cho version này, hiển thị dialog
                runOnUiThread {
                    showChangelogDialog(currentVersion)
                }
            }
        }
    }
    
    /**
     * Hiển thị dialog changelog
     */
    private fun showChangelogDialog(versionName: String) {
        val dialog = ChangelogDialog(this)
        dialog.setVersionName(versionName)
        dialog.setOnDismissListener {
            // Khi đóng dialog, lưu lại đã xem
            lifecycleScope.launch {
                preferencesManager.markChangelogViewedForVersion(versionName)
            }
        }
        dialog.show()
    }
}



