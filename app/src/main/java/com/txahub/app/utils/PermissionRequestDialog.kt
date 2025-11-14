package com.txahub.app.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.txahub.app.R

class PermissionRequestDialog(private val activity: Activity) {
    
    private val permissionManager = PermissionManager(activity)
    
    /**
     * Hiển thị dialog yêu cầu quyền
     */
    fun show(callback: (allGranted: Boolean) -> Unit) {
        val missingPermissions = permissionManager.getMissingPermissions()
        
        if (missingPermissions.isEmpty()) {
            // Tất cả quyền đã được cấp
            callback(true)
            return
        }
        
        // Tạo danh sách quyền để hiển thị
        val permissionItems = missingPermissions.map { permission ->
            PermissionItem(
                permission = permission,
                isGranted = false
            )
        }
        
        // Tạo dialog
        val dialogView = activity.layoutInflater.inflate(R.layout.dialog_permission_request, null)
        val dialog = AlertDialog.Builder(activity)
            .setTitle(R.string.txa_global_permissions_required)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        
        // Setup RecyclerView hoặc LinearLayout để hiển thị danh sách quyền
        // Ở đây tôi sẽ dùng LinearLayout đơn giản
        val layoutPermissions = dialogView.findViewById<android.widget.LinearLayout>(R.id.layoutPermissions)
        val btnContinue = dialogView.findViewById<android.widget.Button>(R.id.btnContinue)
        
        // Xóa các view cũ
        layoutPermissions.removeAllViews()
        
        // Thêm các item quyền
        permissionItems.forEach { item ->
            val itemView = createPermissionItemView(item)
            layoutPermissions.addView(itemView)
        }
        
        // Xử lý nút Continue
        btnContinue.setOnClickListener {
            // Kiểm tra lại quyền
            val stillMissing = permissionManager.getMissingPermissions()
            if (stillMissing.isEmpty()) {
                dialog.dismiss()
                callback(true)
            } else {
                // Vẫn còn quyền chưa cấp, nhưng vẫn cho phép tiếp tục
                dialog.dismiss()
                callback(false)
            }
        }
        
        dialog.show()
    }
    
    /**
     * Tạo view cho một item quyền
     */
    private fun createPermissionItemView(item: PermissionItem): android.view.View {
        val itemView = activity.layoutInflater.inflate(R.layout.item_permission, null)
        val tvPermissionName = itemView.findViewById<android.widget.TextView>(R.id.tvPermissionName)
        val tvPermissionDesc = itemView.findViewById<android.widget.TextView>(R.id.tvPermissionDesc)
        val ivCheck = itemView.findViewById<android.widget.ImageView>(R.id.ivCheck)
        val btnGrant = itemView.findViewById<android.widget.Button>(R.id.btnGrant)
        
        tvPermissionName.text = item.permission.name
        tvPermissionDesc.text = item.permission.description
        
        // Cập nhật trạng thái
        updatePermissionItemView(itemView, item)
        
        // Xử lý click vào item hoặc nút Grant
        val clickListener = android.view.View.OnClickListener {
            requestPermission(item.permission)
        }
        
        itemView.setOnClickListener(clickListener)
        btnGrant.setOnClickListener(clickListener)
        
        return itemView
    }
    
    /**
     * Cập nhật view của item quyền
     */
    private fun updatePermissionItemView(itemView: android.view.View, item: PermissionItem) {
        val ivCheck = itemView.findViewById<android.widget.ImageView>(R.id.ivCheck)
        val btnGrant = itemView.findViewById<android.widget.Button>(R.id.btnGrant)
        val tvStatus = itemView.findViewById<android.widget.TextView>(R.id.tvStatus)
        
        val isGranted = permissionManager.getAllPermissionInfos()
            .firstOrNull { it.permission == item.permission.permission }?.isGranted ?: false
        
        if (isGranted) {
            ivCheck.visibility = android.view.View.VISIBLE
            btnGrant.visibility = android.view.View.GONE
            tvStatus?.text = activity.getString(R.string.txa_global_permission_granted)
            tvStatus?.setTextColor(activity.getColor(android.R.color.holo_green_dark))
        } else {
            ivCheck.visibility = android.view.View.GONE
            btnGrant.visibility = android.view.View.VISIBLE
            tvStatus?.text = activity.getString(R.string.txa_global_permission_not_granted)
            tvStatus?.setTextColor(activity.getColor(android.R.color.holo_red_dark))
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
                Toast.makeText(
                    activity,
                    activity.getString(R.string.txa_global_permission_denied_toast, permissionInfo.name),
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    activity,
                    "Không thể mở cài đặt: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    private data class PermissionItem(
        val permission: PermissionInfo,
        val isGranted: Boolean
    )
}

