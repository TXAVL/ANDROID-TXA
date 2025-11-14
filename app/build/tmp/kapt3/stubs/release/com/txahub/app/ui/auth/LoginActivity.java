package com.txahub.app.ui.auth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u000eH\u0002J\b\u0010\u0010\u001a\u00020\u000eH\u0002J\b\u0010\u0011\u001a\u00020\u000eH\u0002J\u0012\u0010\u0012\u001a\u00020\u000e2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0010\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0019\u001a\u00020\u000eH\u0002J\u0010\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u001c\u001a\u00020\u000eH\u0002J\b\u0010\u001d\u001a\u00020\u000eH\u0002J\u0018\u0010\u001e\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u0017H\u0002J(\u0010 \u001a\u00020\b2\u0006\u0010!\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/txahub/app/ui/auth/LoginActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "authRepository", "Lcom/txahub/app/data/repository/AuthRepository;", "binding", "Lcom/txahub/app/databinding/ActivityLoginBinding;", "isRegisterMode", "", "passkeyManager", "Lcom/txahub/app/utils/PasskeyManager;", "preferencesManager", "Lcom/txahub/app/data/local/PreferencesManager;", "handleLogin", "", "handlePasskeyLogin", "handleRegister", "navigateToMain", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "resendVerification", "email", "", "sendForgotPasswordEmail", "setupUI", "showEmailVerificationDialog", "showEmailVerificationRequiredDialog", "showForgotPasswordDialog", "toggleMode", "validateLoginInput", "password", "validateRegisterInput", "name", "confirmPassword", "app_release"})
public final class LoginActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.txahub.app.databinding.ActivityLoginBinding binding;
    private com.txahub.app.data.repository.AuthRepository authRepository;
    private com.txahub.app.data.local.PreferencesManager preferencesManager;
    private com.txahub.app.utils.PasskeyManager passkeyManager;
    private boolean isRegisterMode = false;
    
    public LoginActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupUI() {
    }
    
    private final void toggleMode() {
    }
    
    private final void handleLogin() {
    }
    
    private final void handleRegister() {
    }
    
    private final boolean validateLoginInput(java.lang.String email, java.lang.String password) {
        return false;
    }
    
    private final boolean validateRegisterInput(java.lang.String name, java.lang.String email, java.lang.String password, java.lang.String confirmPassword) {
        return false;
    }
    
    private final void showEmailVerificationRequiredDialog(java.lang.String email) {
    }
    
    private final void showEmailVerificationDialog(java.lang.String email) {
    }
    
    private final void resendVerification(java.lang.String email) {
    }
    
    private final void showForgotPasswordDialog() {
    }
    
    private final void sendForgotPasswordEmail(java.lang.String email) {
    }
    
    private final void handlePasskeyLogin() {
    }
    
    private final void navigateToMain() {
    }
}