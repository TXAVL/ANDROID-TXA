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
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private var isAdmin = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        preferencesManager = PreferencesManager(this)
        authRepository = AuthRepository(preferencesManager)
        
        setupUI()
        setupNavigationDrawer()
        loadDashboard()
        checkAdminStatus()
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
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.txa_global_menu_dashboard,
            R.string.txa_global_menu_dashboard
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationItemSelected(menuItem)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        
        // Set dashboard as selected by default
        binding.navigationView.setCheckedItem(R.id.nav_dashboard)
    }
    
    private fun handleNavigationItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.nav_dashboard -> {
                true
            }
            R.id.nav_logs -> {
                startActivity(Intent(this, LogViewerActivity::class.java))
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
                if (isAdmin) {
                    startActivity(Intent(this, AdminManagementActivity::class.java))
                } else {
                    Toast.makeText(this, getString(R.string.txa_global_error_no_access), Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.nav_logout -> {
                showLogoutDialog()
                true
            }
            else -> false
        }
    }
    
    private fun checkAdminStatus() {
        lifecycleScope.launch {
            isAdmin = authRepository.isAdmin()
            binding.fabAdmin.visibility = if (isAdmin) {
                android.view.View.VISIBLE
            } else {
                android.view.View.GONE
            }
            // Show/hide admin menu item in drawer
            binding.navigationView.menu.findItem(R.id.nav_admin).isVisible = isAdmin
            invalidateOptionsMenu()
        }
    }
    
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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
        loadDashboard()
    }
}



