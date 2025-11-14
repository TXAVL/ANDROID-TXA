package com.txahub.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0012\u0010\u0014\u001a\u00020\u00132\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0013H\u0002J\b\u0010\u0018\u001a\u00020\u0013H\u0002J\b\u0010\u0019\u001a\u00020\u0013H\u0002J\b\u0010\u001a\u001a\u00020\u0013H\u0002J\b\u0010\u001b\u001a\u00020\u0013H\u0002J\u0012\u0010\u001c\u001a\u00020\u00132\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0014J\b\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020\u0013H\u0002J\b\u0010\"\u001a\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/txahub/app/ui/SettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/txahub/app/databinding/ActivitySettingsBinding;", "currentLanguage", "", "logSettingsManager", "Lcom/txahub/app/utils/LogSettingsManager;", "passkeyManager", "Lcom/txahub/app/utils/PasskeyManager;", "preferencesManager", "Lcom/txahub/app/data/local/PreferencesManager;", "selectedLanguage", "ttsManager", "Lcom/txahub/app/utils/NotificationTTSManager;", "updateChecker", "Lcom/txahub/app/utils/UpdateChecker;", "checkUpdate", "", "handleDeepLink", "intent", "Landroid/content/Intent;", "handleRegisterPasskey", "loadCurrentLanguage", "loadLogSettings", "loadSystemInfo", "loadTTSSettings", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "setupUI", "updateLanguage", "app_release"})
public final class SettingsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.txahub.app.databinding.ActivitySettingsBinding binding;
    private com.txahub.app.data.local.PreferencesManager preferencesManager;
    private com.txahub.app.utils.LogSettingsManager logSettingsManager;
    private com.txahub.app.utils.UpdateChecker updateChecker;
    private com.txahub.app.utils.NotificationTTSManager ttsManager;
    private com.txahub.app.utils.PasskeyManager passkeyManager;
    @org.jetbrains.annotations.NotNull
    private java.lang.String currentLanguage = "auto";
    @org.jetbrains.annotations.NotNull
    private java.lang.String selectedLanguage = "auto";
    
    public SettingsActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void handleDeepLink(android.content.Intent intent) {
    }
    
    private final void setupUI() {
    }
    
    private final void loadCurrentLanguage() {
    }
    
    private final void loadSystemInfo() {
    }
    
    private final void loadLogSettings() {
    }
    
    private final void loadTTSSettings() {
    }
    
    private final void handleRegisterPasskey() {
    }
    
    private final void checkUpdate() {
    }
    
    private final void updateLanguage() {
    }
    
    @java.lang.Override
    public boolean onSupportNavigateUp() {
        return false;
    }
}