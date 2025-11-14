package com.txahub.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u000b\u001a\u00020\f2\u0014\u0010\r\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0004\u0012\u00020\f0\u000eJ \u0010\u0010\u001a\u00020\f2\u0018\u0010\r\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u0004\u0012\u00020\f0\u000eJ$\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00062\u0014\u0010\r\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0012\u0004\u0012\u00020\f0\u000eJ\u0006\u0010\u0015\u001a\u00020\u0006J\u0006\u0010\u0016\u001a\u00020\u0017J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0006H\u0002JB\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00062\u0014\u0010\r\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0012\u0004\u0012\u00020\f0\u000e2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\f0\u000eH\u0002J:\u0010\u001f\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u00062\u0014\u0010\r\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0004\u0012\u00020\f0\u000e2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\f0\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/txahub/app/utils/UpdateChecker;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "API_URL_ALL_CHANGELOGS", "", "API_URL_LASTEST", "API_URL_LATEST", "logWriter", "Lcom/txahub/app/utils/LogWriter;", "checkUpdate", "", "callback", "Lkotlin/Function1;", "Lcom/txahub/app/utils/UpdateInfo;", "getAllChangelogs", "", "Lcom/txahub/app/utils/VersionChangelog;", "getChangelogForVersion", "versionName", "getCurrentVersion", "getCurrentVersionCode", "", "isNewerVersion", "", "newVersion", "currentVersion", "tryGetChangelog", "apiUrl", "onComplete", "tryUpdate", "app_release"})
public final class UpdateChecker {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String API_URL_LATEST = "https://software.txahub.click/product/txahubapp/latest";
    @org.jetbrains.annotations.NotNull
    private final java.lang.String API_URL_LASTEST = "https://software.txahub.click/product/txahubapp/lastest";
    @org.jetbrains.annotations.NotNull
    private final java.lang.String API_URL_ALL_CHANGELOGS = "https://software.txahub.click/product/txahubapp/changelogs";
    @org.jetbrains.annotations.NotNull
    private final com.txahub.app.utils.LogWriter logWriter = null;
    
    public UpdateChecker(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Lấy version hiện tại của app
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCurrentVersion() {
        return null;
    }
    
    /**
     * Lấy version code hiện tại
     */
    public final int getCurrentVersionCode() {
        return 0;
    }
    
    /**
     * So sánh version
     * Trả về true nếu version mới hơn version hiện tại
     */
    private final boolean isNewerVersion(java.lang.String newVersion, java.lang.String currentVersion) {
        return false;
    }
    
    /**
     * Lấy changelog cho một version cụ thể
     * Thử /latest trước, nếu fail thì fallback về /lastest
     */
    public final void getChangelogForVersion(@org.jetbrains.annotations.NotNull
    java.lang.String versionName, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Thử lấy changelog từ một URL cụ thể
     */
    private final void tryGetChangelog(java.lang.String apiUrl, java.lang.String versionName, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> callback, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onComplete) {
    }
    
    /**
     * Kiểm tra cập nhật từ API
     * Thử /latest trước, nếu fail thì fallback về /lastest
     */
    public final void checkUpdate(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.txahub.app.utils.UpdateInfo, kotlin.Unit> callback) {
    }
    
    /**
     * Thử lấy update từ một URL cụ thể
     */
    private final void tryUpdate(java.lang.String apiUrl, kotlin.jvm.functions.Function1<? super com.txahub.app.utils.UpdateInfo, kotlin.Unit> callback, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onComplete) {
    }
    
    /**
     * Lấy tất cả changelog của mọi phiên bản
     */
    public final void getAllChangelogs(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.util.List<com.txahub.app.utils.VersionChangelog>, kotlin.Unit> callback) {
    }
}