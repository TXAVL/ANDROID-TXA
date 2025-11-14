package com.txahub.app.ui.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.txahub.app.R
import com.txahub.app.databinding.ActivityAdminManagementBinding
import com.txahub.app.data.local.PreferencesManager
import com.txahub.app.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AdminManagementActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAdminManagementBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var preferencesManager: PreferencesManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        preferencesManager = PreferencesManager(this)
        authRepository = AuthRepository(preferencesManager)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        setupUI()
        checkAdminAccess()
    }
    
    private fun setupUI() {
        binding.tvAdminInfo.text = getString(R.string.txa_admin_global_admin_info)
        
        // TODO: Thêm các chức năng quản lý admin
        // - Quản lý users
        // - Quản lý links
        // - Quản lý projects
        // - Thống kê tổng quan
        // - Cài đặt hệ thống
    }
    
    private fun checkAdminAccess() {
        lifecycleScope.launch {
            val isAdmin = authRepository.isAdmin()
            if (!isAdmin) {
                Toast.makeText(
                    this@AdminManagementActivity,
                    getString(R.string.txa_admin_global_no_access),
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}



