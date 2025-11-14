package com.txahub.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u000bH\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u00042\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002J\u0012\u0010\u0012\u001a\u00020\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0012\u0010\u0015\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\b\u0010\u0016\u001a\u00020\u000bH\u0014J\b\u0010\u0017\u001a\u00020\u000bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/txahub/app/ui/SplashActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "hasShownChangelog", "", "hasShownPermissions", "permissionManager", "Lcom/txahub/app/utils/PermissionManager;", "preferencesManager", "Lcom/txahub/app/data/local/PreferencesManager;", "checkChangelogAndProceed", "", "checkPermissionsAndProceed", "getCurrentVersion", "", "handleDeepLink", "intent", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onNewIntent", "onResume", "proceedToNextScreen", "app_release"})
public final class SplashActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.txahub.app.data.local.PreferencesManager preferencesManager;
    private com.txahub.app.utils.PermissionManager permissionManager;
    private boolean hasShownChangelog = false;
    private boolean hasShownPermissions = false;
    
    public SplashActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    protected void onNewIntent(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
    }
    
    private final boolean handleDeepLink(android.content.Intent intent) {
        return false;
    }
    
    private final void checkPermissionsAndProceed() {
    }
    
    private final void checkChangelogAndProceed() {
    }
    
    private final void proceedToNextScreen() {
    }
    
    private final java.lang.String getCurrentVersion() {
        return null;
    }
    
    @java.lang.Override
    protected void onResume() {
    }
}