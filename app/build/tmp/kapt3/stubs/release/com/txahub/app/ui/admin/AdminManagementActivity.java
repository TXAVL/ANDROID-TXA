package com.txahub.app.ui.admin;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/txahub/app/ui/admin/AdminManagementActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "authRepository", "Lcom/txahub/app/data/repository/AuthRepository;", "binding", "Lcom/txahub/app/databinding/ActivityAdminManagementBinding;", "preferencesManager", "Lcom/txahub/app/data/local/PreferencesManager;", "checkAdminAccess", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "setupUI", "app_release"})
public final class AdminManagementActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.txahub.app.databinding.ActivityAdminManagementBinding binding;
    private com.txahub.app.data.repository.AuthRepository authRepository;
    private com.txahub.app.data.local.PreferencesManager preferencesManager;
    
    public AdminManagementActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupUI() {
    }
    
    private final void checkAdminAccess() {
    }
    
    @java.lang.Override
    public boolean onSupportNavigateUp() {
        return false;
    }
}