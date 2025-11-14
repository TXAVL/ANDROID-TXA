package com.txahub.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u0006\u0010\n\u001a\u00020\u000bJ\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u0006\u0010\r\u001a\u00020\u000bJ\u0006\u0010\u000e\u001a\u00020\u000bJ\u0006\u0010\u000f\u001a\u00020\u0006J\u0006\u0010\u0010\u001a\u00020\u0006J\u0006\u0010\u0011\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/txahub/app/utils/PermissionManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "areAllPermissionsGranted", "", "getAllPermissionInfos", "", "Lcom/txahub/app/utils/PermissionInfo;", "getBatteryOptimizationSettingsIntent", "Landroid/content/Intent;", "getMissingPermissions", "getNotificationSettingsIntent", "getStorageSettingsIntent", "hasBatteryOptimizationPermission", "hasNotificationPermission", "hasStoragePermission", "Companion", "app_release"})
public final class PermissionManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> REQUIRED_PERMISSIONS = null;
    @org.jetbrains.annotations.NotNull
    public static final com.txahub.app.utils.PermissionManager.Companion Companion = null;
    
    public PermissionManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Kiểm tra quyền thông báo
     */
    public final boolean hasNotificationPermission() {
        return false;
    }
    
    /**
     * Kiểm tra quyền ghi file
     * Android 11+: Cần MANAGE_EXTERNAL_STORAGE (để ghi vào Downloads/TXAAPP)
     * Android < 11: Cần WRITE_EXTERNAL_STORAGE
     */
    public final boolean hasStoragePermission() {
        return false;
    }
    
    /**
     * Kiểm tra quyền bỏ qua tối ưu hóa pin
     */
    public final boolean hasBatteryOptimizationPermission() {
        return false;
    }
    
    /**
     * Lấy Intent để mở cài đặt bỏ qua tối ưu hóa pin
     */
    @org.jetbrains.annotations.NotNull
    public final android.content.Intent getBatteryOptimizationSettingsIntent() {
        return null;
    }
    
    /**
     * Lấy Intent để mở cài đặt thông báo
     */
    @org.jetbrains.annotations.NotNull
    public final android.content.Intent getNotificationSettingsIntent() {
        return null;
    }
    
    /**
     * Lấy Intent để mở cài đặt lưu trữ
     */
    @org.jetbrains.annotations.NotNull
    public final android.content.Intent getStorageSettingsIntent() {
        return null;
    }
    
    /**
     * Lấy danh sách tất cả quyền cần kiểm tra
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.txahub.app.utils.PermissionInfo> getAllPermissionInfos() {
        return null;
    }
    
    /**
     * Kiểm tra xem tất cả quyền đã được cấp chưa
     */
    public final boolean areAllPermissionsGranted() {
        return false;
    }
    
    /**
     * Lấy danh sách quyền chưa được cấp
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.txahub.app.utils.PermissionInfo> getMissingPermissions() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/txahub/app/utils/PermissionManager$Companion;", "", "()V", "REQUIRED_PERMISSIONS", "", "", "getREQUIRED_PERMISSIONS", "()Ljava/util/List;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getREQUIRED_PERMISSIONS() {
            return null;
        }
    }
}