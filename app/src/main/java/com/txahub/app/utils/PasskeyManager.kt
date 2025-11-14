package com.txahub.app.utils

import android.content.Context

/**
 * PasskeyManager - Tính năng sẽ được phát triển sau
 * 
 * File này được giữ lại để tham khảo khi phát triển tính năng Passkey trong tương lai.
 * Hiện tại tính năng Passkey chưa được implement và sẽ được phát triển trong các phiên bản sau.
 */
class PasskeyManager(private val context: Context) {
    
    /**
     * Kiểm tra xem thiết bị có hỗ trợ passkey không
     * Hiện tại luôn trả về false vì tính năng chưa được implement
     */
    fun isPasskeySupported(): Boolean {
        return false
    }
}
