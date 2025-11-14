package com.txahub.app.utils;

/**
 * Quản lý cài đặt bật/tắt các loại log
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\bJ\u0006\u0010\n\u001a\u00020\bJ\u0006\u0010\u000b\u001a\u00020\bJ\u0006\u0010\f\u001a\u00020\bJ\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\bJ\u000e\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\bJ\u000e\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\bJ\u000e\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/txahub/app/utils/LogSettingsManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "isApiLogEnabled", "", "isAppLogEnabled", "isCrashLogEnabled", "isPasskeyLogEnabled", "isUpdateCheckLogEnabled", "resetToDefaults", "", "setApiLogEnabled", "enabled", "setAppLogEnabled", "setCrashLogEnabled", "setPasskeyLogEnabled", "setUpdateCheckLogEnabled", "Companion", "app_release"})
public final class LogSettingsManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "txahub_log_settings_prefs";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_LOG_API_ENABLED = "log_api_enabled";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_LOG_APP_ENABLED = "log_app_enabled";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_LOG_CRASH_ENABLED = "log_crash_enabled";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_LOG_UPDATE_CHECK_ENABLED = "log_update_check_enabled";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_LOG_PASSKEY_ENABLED = "log_passkey_enabled";
    private static final boolean DEFAULT_ENABLED = true;
    @org.jetbrains.annotations.NotNull
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull
    public static final com.txahub.app.utils.LogSettingsManager.Companion Companion = null;
    
    public LogSettingsManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Kiểm tra xem log API có được bật không
     */
    public final boolean isApiLogEnabled() {
        return false;
    }
    
    /**
     * Bật/tắt log API
     */
    public final void setApiLogEnabled(boolean enabled) {
    }
    
    /**
     * Kiểm tra xem log App có được bật không
     */
    public final boolean isAppLogEnabled() {
        return false;
    }
    
    /**
     * Bật/tắt log App
     */
    public final void setAppLogEnabled(boolean enabled) {
    }
    
    /**
     * Kiểm tra xem log Crash có được bật không
     */
    public final boolean isCrashLogEnabled() {
        return false;
    }
    
    /**
     * Bật/tắt log Crash
     */
    public final void setCrashLogEnabled(boolean enabled) {
    }
    
    /**
     * Kiểm tra xem log Update Check có được bật không
     */
    public final boolean isUpdateCheckLogEnabled() {
        return false;
    }
    
    /**
     * Bật/tắt log Update Check
     */
    public final void setUpdateCheckLogEnabled(boolean enabled) {
    }
    
    /**
     * Kiểm tra xem log Passkey có được bật không
     */
    public final boolean isPasskeyLogEnabled() {
        return false;
    }
    
    /**
     * Bật/tắt log Passkey
     */
    public final void setPasskeyLogEnabled(boolean enabled) {
    }
    
    /**
     * Reset tất cả về mặc định (bật tất cả)
     */
    public final void resetToDefaults() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/txahub/app/utils/LogSettingsManager$Companion;", "", "()V", "DEFAULT_ENABLED", "", "KEY_LOG_API_ENABLED", "", "KEY_LOG_APP_ENABLED", "KEY_LOG_CRASH_ENABLED", "KEY_LOG_PASSKEY_ENABLED", "KEY_LOG_UPDATE_CHECK_ENABLED", "PREFS_NAME", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}