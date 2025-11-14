package com.txahub.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J*\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\f\u001a\u0004\u0018\u00010\rH\u0086@\u00f8\u0001\u0002\u00a2\u0006\u0002\u0010\u000eJ\u0011\u0010\u000f\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0002\u00a2\u0006\u0002\u0010\u000eJ\u0011\u0010\u0011\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0002\u00a2\u0006\u0002\u0010\u000eJ2\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0011\u0010\u0017\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0002\u00a2\u0006\u0002\u0010\u000eJB\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u00062\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u001b\u0010\u001cJ*\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u001e\u0010\u000bJB\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010 \u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b!\u0010\u001cJ2\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010 \u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b#\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006$"}, d2 = {"Lcom/txahub/app/data/repository/AuthRepository;", "", "preferencesManager", "Lcom/txahub/app/data/local/PreferencesManager;", "(Lcom/txahub/app/data/local/PreferencesManager;)V", "forgotPassword", "Lkotlin/Result;", "", "email", "", "forgotPassword-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentUser", "Lcom/txahub/app/data/models/User;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isAdmin", "", "isLoggedIn", "login", "Lcom/txahub/app/data/models/AuthResponse;", "password", "login-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "register", "name", "passwordConfirmation", "register-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resendVerification", "resendVerification-gIAlu-s", "resetPassword", "token", "resetPassword-yxL6bBk", "verifyEmail", "verifyEmail-0E7RQCE", "app_release"})
public final class AuthRepository {
    @org.jetbrains.annotations.NotNull
    private final com.txahub.app.data.local.PreferencesManager preferencesManager = null;
    
    public AuthRepository(@org.jetbrains.annotations.NotNull
    com.txahub.app.data.local.PreferencesManager preferencesManager) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object logout(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object isLoggedIn(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getCurrentUser(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.txahub.app.data.models.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object isAdmin(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}