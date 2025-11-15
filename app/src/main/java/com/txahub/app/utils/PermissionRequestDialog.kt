package com.txahub.app.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.txahub.app.R

/**
 * Dialog yêu cầu quyền mới - được thiết kế lại từ đầu
 * - Hiển thị tất cả quyền (đã cấp hoặc chưa)
 * - Chỉ cho phép đóng khi tất cả quyền đã được cấp
 * - Tự động refresh khi quay lại từ Settings
 */
class PermissionRequestDialog(private val activity: Activity) {
    
    private val permissionManager = PermissionManager(activity)
    private var dialog: AlertDialog? = null
    private var layoutPermissions: android.widget.LinearLayout? = null
    private var btnClose: android.widget.Button? = null
    private var callback: ((Boolean) -> Unit)? = null
    
    // Lưu trữ các view của từng quyền để có thể refresh
    private val permissionViews = mutableMapOf<String, android.view.View>()
    
    /**
     * Hiển thị dialog yêu cầu quyền
     */
    fun show(callback: (allGranted: Boolean) -> Unit) {
        this.callback = callback
        
        // Tạo dialog
        val dialogView = activity.layoutInflater.inflate(R.layout.dialog_permission_request, null)
        dialog = AlertDialog.Builder(activity)
            .setTitle(R.string.txa_global.permissions_required)
            .setView(dialogView)
            .setCancelable(false) // Không cho phép đóng bằng cách bấm ra ngoài hoặc nút back
            .create()
        
        // Setup views
        layoutPermissions = dialogView.findViewById<android.widget.LinearLayout>(R.id.layoutPermissions)
        btnClose = dialogView.findViewById<android.widget.Button>(R.id.btnContinue)
        
        // Đổi text nút thành "Đóng"
        btnClose?.text = activity.getString(R.string.txa_global.close)
        
        // Xử lý nút Đóng
        btnClose?.setOnClickListener {
            // Chỉ cho phép đóng khi tất cả quyền đã được cấp
            if (permissionManager.areAllPermissionsGranted()) {
                dialog?.dismiss()
                callback(true)
            } else {
                // Không cho phép đóng, hiển thị thông báo
                Toast.makeText(
                    activity,
                    "Vui lòng cấp đầy đủ quyền để tiếp tục",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        
        // Refresh danh sách quyền lần đầu
        refreshPermissionList()
        
        dialog?.show()
    }
    
    /**
     * Refresh danh sách quyền (gọi khi quay lại từ Settings)
     */
    fun refreshPermissionList() {
        // Lấy TẤT CẢ quyền (kể cả đã cấp)
        val allPermissions = permissionManager.getAllPermissionInfos()
        
        // Xóa các view cũ
        layoutPermissions?.removeAllViews()
        permissionViews.clear()
        
        // Tạo view cho từng quyền
        allPermissions.forEach { permissionInfo ->
            val itemView = createPermissionItemView(permissionInfo)
            layoutPermissions?.addView(itemView)
            permissionViews[permissionInfo.permission] = itemView
        }
        
        // Cập nhật trạng thái nút Đóng
        updateCloseButtonState()
    }
    
    /**
     * Tạo view cho một item quyền
     */
    private fun createPermissionItemView(permissionInfo: PermissionInfo): android.view.View {
        val itemView = activity.layoutInflater.inflate(R.layout.item_permission, null)
        val tvPermissionName = itemView.findViewById<android.widget.TextView>(R.id.tvPermissionName)
        val tvPermissionDesc = itemView.findViewById<android.widget.TextView>(R.id.tvPermissionDesc)
        val btnGrant = itemView.findViewById<android.widget.Button>(R.id.btnGrant)
        val ivCheck = itemView.findViewById<android.widget.ImageView>(R.id.ivCheck)
        val tvStatus = itemView.findViewById<android.widget.TextView>(R.id.tvStatus)
        
        tvPermissionName.text = permissionInfo.name
        tvPermissionDesc.text = permissionInfo.description
        
        // Cập nhật trạng thái ban đầu
        updatePermissionItemView(itemView, permissionInfo)
        
        // Xử lý click vào nút Cấp quyền
        btnGrant.setOnClickListener {
            requestPermission(permissionInfo)
        }
        
        // Xử lý click vào toàn bộ item (chỉ khi chưa cấp)
        itemView.setOnClickListener {
            // Chỉ mở settings nếu quyền chưa được cấp
            val currentPermissionInfo = permissionManager.getAllPermissionInfos()
                .firstOrNull { it.permission == permissionInfo.permission }
            if (currentPermissionInfo?.isGranted != true) {
                requestPermission(permissionInfo)
            }
        }
        
        return itemView
    }
    
    /**
     * Cập nhật view của item quyền
     */
    private fun updatePermissionItemView(itemView: android.view.View, permissionInfo: PermissionInfo) {
        val ivCheck = itemView.findViewById<android.widget.ImageView>(R.id.ivCheck)
        val btnGrant = itemView.findViewById<android.widget.Button>(R.id.btnGrant)
        val tvStatus = itemView.findViewById<android.widget.TextView>(R.id.tvStatus)
        
        // Kiểm tra lại trạng thái quyền (để đảm bảo luôn cập nhật đúng)
        val currentPermissionInfo = permissionManager.getAllPermissionInfos()
            .firstOrNull { it.permission == permissionInfo.permission }
        
        val isGranted = currentPermissionInfo?.isGranted ?: false
        
        if (isGranted) {
            // Quyền đã cấp: hiện tích xanh, ẩn nút Cấp quyền
            ivCheck.visibility = android.view.View.VISIBLE
            ivCheck.setImageResource(android.R.drawable.checkbox_on_background)
            ivCheck.setColorFilter(activity.getColor(android.R.color.holo_green_dark))
            btnGrant.visibility = android.view.View.GONE
            tvStatus?.text = activity.getString(R.string.txa_global.permission_granted)
            tvStatus?.setTextColor(activity.getColor(android.R.color.holo_green_dark))
            itemView.alpha = 1f
            itemView.isClickable = false // Không cho click khi đã cấp
        } else {
            // Quyền chưa cấp: ẩn tích, hiện nút Cấp quyền
            ivCheck.visibility = android.view.View.GONE
            btnGrant.visibility = android.view.View.VISIBLE
            tvStatus?.text = activity.getString(R.string.txa_global.permission_not_granted)
            tvStatus?.setTextColor(activity.getColor(android.R.color.holo_red_dark))
            itemView.alpha = 1f
            itemView.isClickable = true // Cho phép click khi chưa cấp
        }
    }
    
    /**
     * Cập nhật trạng thái nút Đóng
     */
    private fun updateCloseButtonState() {
        val allGranted = permissionManager.areAllPermissionsGranted()
        btnClose?.isEnabled = allGranted
        
        if (allGranted) {
            btnClose?.alpha = 1f
        } else {
            btnClose?.alpha = 0.5f
        }
    }
    
    /**
     * Yêu cầu quyền
     */
    private fun requestPermission(permissionInfo: PermissionInfo) {
        if (permissionInfo.settingsIntent != null) {
            // Mở cài đặt
            try {
                activity.startActivity(permissionInfo.settingsIntent)
                // Không dismiss dialog, để user quay lại và refresh
            } catch (e: Exception) {
                Toast.makeText(
                    activity,
                    "Không thể mở cài đặt: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    /**
     * Đóng dialog (nếu đang hiển thị)
     */
    fun dismiss() {
        dialog?.dismiss()
        dialog = null
        permissionViews.clear()
    }
}
