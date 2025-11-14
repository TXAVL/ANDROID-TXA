package com.txahub.app.utils;

/**
 * Quản lý cài đặt notification grouping cho Android Auto
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\bJ\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/txahub/app/utils/AndroidAutoGroupingManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "getGroupId", "", "getGroupName", "isGroupingEnabled", "", "setGroupingEnabled", "", "enabled", "Companion", "app_release"})
public final class AndroidAutoGroupingManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "txahub_android_auto_grouping_prefs";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_GROUPING_ENABLED = "grouping_enabled";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String GROUP_ID = "txahub_notification_group";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String GROUP_NAME = "TXA Hub";
    @org.jetbrains.annotations.NotNull
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull
    public static final com.txahub.app.utils.AndroidAutoGroupingManager.Companion Companion = null;
    
    public AndroidAutoGroupingManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Kiểm tra xem notification grouping có được bật không
     */
    public final boolean isGroupingEnabled() {
        return false;
    }
    
    /**
     * Bật/tắt notification grouping
     */
    public final void setGroupingEnabled(boolean enabled) {
    }
    
    /**
     * Lấy ID của notification group
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGroupId() {
        return null;
    }
    
    /**
     * Lấy tên của notification group
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGroupName() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/txahub/app/utils/AndroidAutoGroupingManager$Companion;", "", "()V", "GROUP_ID", "", "GROUP_NAME", "KEY_GROUPING_ENABLED", "PREFS_NAME", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}