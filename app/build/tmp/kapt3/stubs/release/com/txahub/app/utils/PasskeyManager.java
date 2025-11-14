package com.txahub.app.utils;

/**
 * Quản lý Passkey operations sử dụng Credential Manager API và Fido2ApiClient
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u0000 #2\u00020\u0001:\u0001#B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0013H\u0002J/\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u000fH\u0002\u00a2\u0006\u0002\u0010\u001aJ\u0015\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u000bH\u0002\u00a2\u0006\u0002\u0010\u001dJ\u0006\u0010\u001e\u001a\u00020\u001fJ>\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000f2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b!\u0010\"R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006$"}, d2 = {"Lcom/txahub/app/utils/PasskeyManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "credentialManager", "Landroidx/credentials/CredentialManager;", "fido2ApiClient", "Lcom/google/android/gms/fido/fido2/Fido2ApiClient;", "authenticatePasskey", "Lkotlin/Result;", "error/NonExistentClass", "authenticatePasskey-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "convertAuthenticationResultToJson", "", "result", "Lcom/google/android/gms/fido/fido2/api/common/AuthenticatorAssertionResponse;", "convertRegistrationResultToJson", "Lcom/google/android/gms/fido/fido2/api/common/AuthenticatorAttestationResponse;", "createPublicKeyCredentialCreationOptions", "Lcom/google/android/gms/fido/fido2/api/common/PublicKeyCredentialCreationOptions;", "challengeData", "userName", "userDisplayName", "userIcon", "(Lerror/NonExistentClass;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/google/android/gms/fido/fido2/api/common/PublicKeyCredentialCreationOptions;", "createPublicKeyCredentialRequestOptions", "Lcom/google/android/gms/fido/fido2/api/common/PublicKeyCredentialRequestOptions;", "(Lerror/NonExistentClass;)Lcom/google/android/gms/fido/fido2/api/common/PublicKeyCredentialRequestOptions;", "isPasskeySupported", "", "registerPasskey", "registerPasskey-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_release"})
public final class PasskeyManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.credentials.CredentialManager credentialManager = null;
    @org.jetbrains.annotations.Nullable
    private final com.google.android.gms.fido.fido2.Fido2ApiClient fido2ApiClient = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "PasskeyManager";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String RP_ID = "txahub.click";
    @org.jetbrains.annotations.NotNull
    public static final com.txahub.app.utils.PasskeyManager.Companion Companion = null;
    
    public PasskeyManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Tạo PublicKeyCredentialCreationOptions từ server response
     */
    private final com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialCreationOptions createPublicKeyCredentialCreationOptions(error.NonExistentClass challengeData, java.lang.String userName, java.lang.String userDisplayName, java.lang.String userIcon) {
        return null;
    }
    
    /**
     * Tạo PublicKeyCredentialRequestOptions từ server response
     */
    private final com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialRequestOptions createPublicKeyCredentialRequestOptions(error.NonExistentClass challengeData) {
        return null;
    }
    
    /**
     * Chuyển đổi RegistrationResult thành JSON string
     */
    private final java.lang.String convertRegistrationResultToJson(com.google.android.gms.fido.fido2.api.common.AuthenticatorAttestationResponse result) {
        return null;
    }
    
    /**
     * Chuyển đổi AuthenticationResult thành JSON string
     */
    private final java.lang.String convertAuthenticationResultToJson(com.google.android.gms.fido.fido2.api.common.AuthenticatorAssertionResponse result) {
        return null;
    }
    
    /**
     * Kiểm tra xem thiết bị có hỗ trợ passkey không
     */
    public final boolean isPasskeySupported() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/txahub/app/utils/PasskeyManager$Companion;", "", "()V", "RP_ID", "", "TAG", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}