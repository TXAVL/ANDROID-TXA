package com.txahub.app.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\'\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00040\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\'\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\'\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0012H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\'\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0015H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\'\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0018H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\'\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u001bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001c\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001d"}, d2 = {"Lcom/txahub/app/data/api/AuthApiService;", "", "forgotPassword", "Lretrofit2/Response;", "Lcom/txahub/app/data/models/ApiResponse;", "", "request", "Lcom/txahub/app/data/models/ForgotPasswordRequest;", "(Lcom/txahub/app/data/models/ForgotPasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUser", "Lcom/txahub/app/data/models/User;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lcom/txahub/app/data/models/AuthResponse;", "Lcom/txahub/app/data/models/LoginRequest;", "(Lcom/txahub/app/data/models/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "register", "Lcom/txahub/app/data/models/RegisterRequest;", "(Lcom/txahub/app/data/models/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resendVerification", "Lcom/txahub/app/data/models/ResendVerificationRequest;", "(Lcom/txahub/app/data/models/ResendVerificationRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetPassword", "Lcom/txahub/app/data/models/ResetPasswordRequest;", "(Lcom/txahub/app/data/models/ResetPasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "verifyEmail", "Lcom/txahub/app/data/models/VerifyEmailRequest;", "(Lcom/txahub/app/data/models/VerifyEmailRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public abstract interface AuthApiService {
    
    @retrofit2.http.POST(value = "auth/login")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object login(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.txahub.app.data.models.LoginRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.txahub.app.data.models.ApiResponse<com.txahub.app.data.models.AuthResponse>>> $completion);
    
    @retrofit2.http.POST(value = "auth/register")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object register(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.txahub.app.data.models.RegisterRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.txahub.app.data.models.ApiResponse<com.txahub.app.data.models.AuthResponse>>> $completion);
    
    @retrofit2.http.POST(value = "auth/verify-email")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object verifyEmail(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.txahub.app.data.models.VerifyEmailRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.txahub.app.data.models.ApiResponse<kotlin.Unit>>> $completion);
    
    @retrofit2.http.POST(value = "auth/resend-verification")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object resendVerification(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.txahub.app.data.models.ResendVerificationRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.txahub.app.data.models.ApiResponse<kotlin.Unit>>> $completion);
    
    @retrofit2.http.POST(value = "auth/forgot-password")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object forgotPassword(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.txahub.app.data.models.ForgotPasswordRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.txahub.app.data.models.ApiResponse<kotlin.Unit>>> $completion);
    
    @retrofit2.http.POST(value = "auth/reset-password")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object resetPassword(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.txahub.app.data.models.ResetPasswordRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.txahub.app.data.models.ApiResponse<kotlin.Unit>>> $completion);
    
    @retrofit2.http.GET(value = "user")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUser(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.txahub.app.data.models.ApiResponse<com.txahub.app.data.models.User>>> $completion);
    
    @retrofit2.http.POST(value = "auth/logout")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object logout(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.txahub.app.data.models.ApiResponse<kotlin.Unit>>> $completion);
}