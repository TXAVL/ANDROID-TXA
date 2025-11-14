package com.txahub.app.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u001e\u001a\u00020\u001fJ\b\u0010 \u001a\u00020\u0012H\u0002J\u000e\u0010!\u001a\u00020\u001f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\n \u0019*\u0004\u0018\u00010\u00180\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006$"}, d2 = {"Lcom/txahub/app/data/api/ApiClient;", "", "()V", "BASE_URL", "", "apiLoggingInterceptor", "Lokhttp3/Interceptor;", "authApi", "Lcom/txahub/app/data/api/AuthApiService;", "getAuthApi", "()Lcom/txahub/app/data/api/AuthApiService;", "authInterceptor", "authToken", "context", "Landroid/content/Context;", "loggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "okHttpClient", "Lokhttp3/OkHttpClient;", "passkeyApi", "Lcom/txahub/app/data/api/PasskeyApiService;", "getPasskeyApi", "()Lcom/txahub/app/data/api/PasskeyApiService;", "retrofit", "Lretrofit2/Retrofit;", "kotlin.jvm.PlatformType", "statisticsApi", "Lcom/txahub/app/data/api/StatisticsApiService;", "getStatisticsApi", "()Lcom/txahub/app/data/api/StatisticsApiService;", "clearAuthToken", "", "createOkHttpClient", "initialize", "setAuthToken", "token", "app_release"})
public final class ApiClient {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String BASE_URL = "https://txahub.click/api/";
    @org.jetbrains.annotations.Nullable
    private static java.lang.String authToken;
    @org.jetbrains.annotations.Nullable
    private static android.content.Context context;
    @org.jetbrains.annotations.NotNull
    private static final okhttp3.logging.HttpLoggingInterceptor loggingInterceptor = null;
    @org.jetbrains.annotations.NotNull
    private static final okhttp3.Interceptor apiLoggingInterceptor = null;
    @org.jetbrains.annotations.NotNull
    private static final okhttp3.Interceptor authInterceptor = null;
    @org.jetbrains.annotations.NotNull
    private static final okhttp3.OkHttpClient okHttpClient = null;
    private static final retrofit2.Retrofit retrofit = null;
    @org.jetbrains.annotations.NotNull
    private static final com.txahub.app.data.api.AuthApiService authApi = null;
    @org.jetbrains.annotations.NotNull
    private static final com.txahub.app.data.api.StatisticsApiService statisticsApi = null;
    @org.jetbrains.annotations.NotNull
    private static final com.txahub.app.data.api.PasskeyApiService passkeyApi = null;
    @org.jetbrains.annotations.NotNull
    public static final com.txahub.app.data.api.ApiClient INSTANCE = null;
    
    private ApiClient() {
        super();
    }
    
    public final void initialize(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
    
    private final okhttp3.OkHttpClient createOkHttpClient() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.txahub.app.data.api.AuthApiService getAuthApi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.txahub.app.data.api.StatisticsApiService getStatisticsApi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.txahub.app.data.api.PasskeyApiService getPasskeyApi() {
        return null;
    }
    
    public final void setAuthToken(@org.jetbrains.annotations.NotNull
    java.lang.String token) {
    }
    
    public final void clearAuthToken() {
    }
}