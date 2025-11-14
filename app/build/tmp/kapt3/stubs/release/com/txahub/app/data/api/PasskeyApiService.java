package com.txahub.app.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0004H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J!\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0004H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0004H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\t"}, d2 = {"Lcom/txahub/app/data/api/PasskeyApiService;", "", "createChallenge", "Lretrofit2/Response;", "error/NonExistentClass", "request", "(Lerror/NonExistentClass;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "verifyAuthentication", "verifyRegistration", "app_release"})
public abstract interface PasskeyApiService {
    
    /**
     * Tạo challenge cho passkey registration hoặc authentication
     */
    @retrofit2.http.POST(value = "passkey-api/create_challenge")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createChallenge(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    error.NonExistentClass request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<error.NonExistentClass>> $completion);
    
    /**
     * Xác thực passkey registration
     */
    @retrofit2.http.POST(value = "passkey-api/verify_registration")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object verifyRegistration(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    error.NonExistentClass request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<error.NonExistentClass>> $completion);
    
    /**
     * Xác thực passkey để đăng nhập
     */
    @retrofit2.http.POST(value = "passkey-api/verify_authentication")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object verifyAuthentication(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    error.NonExistentClass request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<error.NonExistentClass>> $completion);
}