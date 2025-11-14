package com.txahub.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u0000 !2\u00020\u0001:\u0001!B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0010\u001a\u00020\u0011J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u0019\u001a\u00020\u0018H\u0002J\u001e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u0018J\u0006\u0010\u001f\u001a\u00020\u0011J\u0006\u0010 \u001a\u00020\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\""}, d2 = {"Lcom/txahub/app/utils/NotificationHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "groupingManager", "Lcom/txahub/app/utils/AndroidAutoGroupingManager;", "getGroupingManager", "()Lcom/txahub/app/utils/AndroidAutoGroupingManager;", "groupingManager$delegate", "Lkotlin/Lazy;", "soundManager", "Lcom/txahub/app/utils/NotificationSoundManager;", "getSoundManager", "()Lcom/txahub/app/utils/NotificationSoundManager;", "soundManager$delegate", "cancelUpdateNotification", "", "createNotificationChannels", "drawableToBitmap", "Landroid/graphics/Bitmap;", "drawable", "Landroid/graphics/drawable/Drawable;", "hasNotificationPermission", "", "isUpdateCheckServiceRunning", "showUpdateNotification", "versionName", "", "downloadUrl", "forceUpdate", "updateNotificationChannelSound", "updateNotificationChannelsGrouping", "Companion", "app_release"})
public final class NotificationHelper {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_ID_UPDATE = "txahub_update_channel";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_NAME_UPDATE = "C\u1eadp nh\u1eadt TXA Hub";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_ID_BACKGROUND = "txahub_background_channel";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_NAME_BACKGROUND = "TXA Hub \u0111ang ch\u1ea1y n\u1ec1n";
    public static final int NOTIFICATION_ID_UPDATE = 1001;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy soundManager$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy groupingManager$delegate = null;
    @org.jetbrains.annotations.NotNull
    public static final com.txahub.app.utils.NotificationHelper.Companion Companion = null;
    
    public NotificationHelper(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    private final com.txahub.app.utils.NotificationSoundManager getSoundManager() {
        return null;
    }
    
    private final com.txahub.app.utils.AndroidAutoGroupingManager getGroupingManager() {
        return null;
    }
    
    /**
     * Tạo các notification channels cho Android 8.0+
     */
    private final void createNotificationChannels() {
    }
    
    /**
     * Kiểm tra quyền thông báo
     */
    public final boolean hasNotificationPermission() {
        return false;
    }
    
    /**
     * Hiển thị thông báo có bản cập nhật mới
     */
    public final void showUpdateNotification(@org.jetbrains.annotations.NotNull
    java.lang.String versionName, @org.jetbrains.annotations.NotNull
    java.lang.String downloadUrl, boolean forceUpdate) {
    }
    
    /**
     * Hủy thông báo cập nhật
     */
    public final void cancelUpdateNotification() {
    }
    
    /**
     * Kiểm tra xem UpdateCheckService có đang chạy không
     */
    private final boolean isUpdateCheckServiceRunning() {
        return false;
    }
    
    /**
     * Cập nhật sound cho notification channel
     * Trên Android 8.0+, không thể update channel đã tồn tại, phải xóa và tạo lại
     * Nếu service đang chạy, sẽ tạm thời stop service để cập nhật channel
     */
    public final void updateNotificationChannelSound() {
    }
    
    /**
     * Cập nhật notification channels grouping dựa trên cài đặt
     */
    public final void updateNotificationChannelsGrouping() {
    }
    
    /**
     * Chuyển đổi Drawable thành Bitmap
     */
    private final android.graphics.Bitmap drawableToBitmap(android.graphics.drawable.Drawable drawable) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/txahub/app/utils/NotificationHelper$Companion;", "", "()V", "CHANNEL_ID_BACKGROUND", "", "CHANNEL_ID_UPDATE", "CHANNEL_NAME_BACKGROUND", "CHANNEL_NAME_UPDATE", "NOTIFICATION_ID_UPDATE", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}