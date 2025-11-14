package com.txahub.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u0000 +2\u00020\u0001:\u0001+B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u001c\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\f\u001a\u00020\rH\u0007J\u001a\u0010\u000e\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\u0002J\u0006\u0010\u0011\u001a\u00020\bJ\u0018\u0010\u0012\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\rJ\u0010\u0010\u0016\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0017\u001a\u00020\nJ\u0010\u0010\u0018\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0017\u001a\u00020\nJ\b\u0010\u0019\u001a\u0004\u0018\u00010\nJ\b\u0010\u001a\u001a\u0004\u0018\u00010\nJ\b\u0010\u001b\u001a\u0004\u0018\u00010\nJ\b\u0010\u001c\u001a\u0004\u0018\u00010\nJ\u0006\u0010\u001d\u001a\u00020\rJ\u0006\u0010\u001e\u001a\u00020\rJ\u0016\u0010\u001f\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\rJ\b\u0010 \u001a\u00020\bH\u0002J\u0010\u0010!\u001a\u00020\r2\u0006\u0010\"\u001a\u00020\rH\u0002J\u0010\u0010#\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\nJ\u0010\u0010$\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\nJ\u000e\u0010%\u001a\u00020\b2\u0006\u0010&\u001a\u00020\rJ \u0010\'\u001a\u0014\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020*0(2\u0006\u0010\u0017\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/txahub/app/utils/NotificationSoundManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "addAllAppSoundsToMediaStore", "", "addAppSoundToMediaStore", "Landroid/net/Uri;", "sourceUri", "displayName", "", "addSingleSoundToMediaStore", "soundFile", "Ljava/io/File;", "clearDefaultAppSounds", "copyRawSoundToInternalStorage", "rawResourceId", "", "fileName", "copySoundToDefaultFolder", "uri", "copySoundToInternalStorage", "getCustomSoundUri", "getDefaultAppSoundUri", "getNotificationSoundUri", "getRawSoundUri", "getSoundDisplayName", "getSoundType", "initializeDefaultAppSound", "removeAllAppSoundsFromMediaStore", "removeVietnameseAccents", "text", "setCustomSoundUri", "setDefaultAppSoundUri", "setSoundType", "type", "validateSoundFile", "Lkotlin/Triple;", "", "", "Companion", "app_release"})
public final class NotificationSoundManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "txahub_notification_sound_prefs";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_SOUND_TYPE = "sound_type";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_CUSTOM_SOUND_URI = "custom_sound_uri";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_DEFAULT_APP_SOUND_URI = "default_app_sound_uri";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String SOUND_FOLDER = "notification_sounds";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CUSTOM_SOUND_FOLDER = "notification_sounds_custom";
    public static final long MAX_SOUND_DURATION_MS = 45000L;
    private static final int MAX_SOUND_SIZE_BYTES = 5242880;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> ALLOWED_MIME_TYPES = null;
    @org.jetbrains.annotations.NotNull
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull
    public static final com.txahub.app.utils.NotificationSoundManager.Companion Companion = null;
    
    public NotificationSoundManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Chuyển đổi chuỗi có dấu sang không dấu (a-z, 0-9, A-Z)
     * Ví dụ: "chuông.mp3" -> "chuong.mp3", "nhạc êm dịu.mp3" -> "nhac_em_diu.mp3"
     */
    private final java.lang.String removeVietnameseAccents(java.lang.String text) {
        return null;
    }
    
    /**
     * Lấy loại nhạc chuông hiện tại
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSoundType() {
        return null;
    }
    
    /**
     * Lưu loại nhạc chuông
     */
    public final void setSoundType(@org.jetbrains.annotations.NotNull
    java.lang.String type) {
    }
    
    /**
     * Lấy URI của custom sound
     */
    @org.jetbrains.annotations.Nullable
    public final android.net.Uri getCustomSoundUri() {
        return null;
    }
    
    /**
     * Lưu URI của custom sound
     */
    public final void setCustomSoundUri(@org.jetbrains.annotations.Nullable
    android.net.Uri uri) {
    }
    
    /**
     * Lấy URI của sound từ raw resource (chuong.mp3)
     * Nếu file đã được copy vào external storage, trả về URI từ đó
     * Nếu chưa, trả về null
     */
    @org.jetbrains.annotations.Nullable
    public final android.net.Uri getRawSoundUri() {
        return null;
    }
    
    /**
     * Lấy URI của nhạc chuông để sử dụng trong notification
     */
    @org.jetbrains.annotations.Nullable
    public final android.net.Uri getNotificationSoundUri() {
        return null;
    }
    
    /**
     * Lấy URI của sound mặc định app (nếu đã set)
     * Đọc tất cả file .mp3 và .wav trong folder notification_sounds
     * Nếu có nhiều file, random chọn 1 file mỗi lần gọi
     * Hỗ trợ file có tên có dấu (tự động chuyển sang không dấu khi copy từ raw resource)
     *
     * Lưu ý: File được lưu trong external storage để có thể truy cập từ máy tính khi cắm cáp USB
     */
    @org.jetbrains.annotations.Nullable
    public final android.net.Uri getDefaultAppSoundUri() {
        return null;
    }
    
    /**
     * Set sound mặc định cho app
     */
    public final void setDefaultAppSoundUri(@org.jetbrains.annotations.Nullable
    android.net.Uri uri) {
    }
    
    /**
     * Lấy tên hiển thị của nhạc chuông hiện tại
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSoundDisplayName() {
        return null;
    }
    
    /**
     * Validate file nhạc chuông trước khi lưu
     * @return Triple<Boolean, String, Long> - (isValid, errorMessage, durationMs)
     * Nếu file quá dài, vẫn trả về isValid=true nhưng durationMs > MAX_SOUND_DURATION_MS
     */
    @org.jetbrains.annotations.NotNull
    public final kotlin.Triple<java.lang.Boolean, java.lang.String, java.lang.Long> validateSoundFile(@org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
    
    /**
     * Copy file nhạc chuông tùy chỉnh vào external storage của app (folder riêng)
     * Có thể truy cập từ máy tính khi cắm cáp USB tại: Android/data/com.txahub.vn.app/files/notification_sounds_custom/
     */
    @org.jetbrains.annotations.Nullable
    public final android.net.Uri copySoundToInternalStorage(@org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
    
    /**
     * Copy file nhạc chuông từ raw resource vào external storage
     * @param rawResourceId ID của file trong res/raw/ (ví dụ: R.raw.chuong)
     * @param fileName Tên file khi lưu vào external storage (tự động chuyển có dấu sang không dấu)
     * @return URI của file đã copy hoặc null nếu lỗi
     *
     * Lưu ý: File được lưu trong external storage để có thể truy cập từ máy tính khi cắm cáp USB
     */
    @org.jetbrains.annotations.Nullable
    public final android.net.Uri copyRawSoundToInternalStorage(int rawResourceId, @org.jetbrains.annotations.NotNull
    java.lang.String fileName) {
        return null;
    }
    
    /**
     * Copy file nhạc chuông vào folder default của app (không phải custom)
     * Lưu trong external storage để có thể truy cập từ máy tính khi cắm cáp USB
     */
    @org.jetbrains.annotations.Nullable
    public final android.net.Uri copySoundToDefaultFolder(@org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
    
    /**
     * Xóa tất cả file trong folder default app sounds
     */
    public final void clearDefaultAppSounds() {
    }
    
    /**
     * Khởi tạo sound mặc định của app từ raw resource (chỉ chạy lần đầu)
     *
     * Lưu ý: Để thêm nhiều file cho random, đặt file trong res/raw/ với tên hợp lệ:
     * - Chỉ chứa a-z, 0-9, A-Z, dấu gạch dưới (ví dụ: chuong.mp3, sound1.mp3, nhac_em_diu.mp3)
     * - Nếu fileName có dấu, sẽ tự động chuyển sang không dấu khi copy vào external storage
     * - Sau khi copy, getDefaultAppSoundUri() sẽ tự động random chọn 1 file nếu có nhiều file
     * - File được lưu trong external storage: Android/data/com.txahub.vn.app/files/notification_sounds/
     *  Có thể truy cập từ máy tính khi cắm cáp USB
     */
    public final void initializeDefaultAppSound(int rawResourceId, @org.jetbrains.annotations.NotNull
    java.lang.String fileName) {
    }
    
    /**
     * Xóa tất cả entry của app trong MediaStore (bắt đầu bằng "TXA Hub")
     */
    private final void removeAllAppSoundsFromMediaStore() {
    }
    
    /**
     * Thêm TẤT CẢ nhạc chuông của app vào MediaStore
     * Mỗi file sẽ có tên riêng: "TXA Hub - [tên file]" (ví dụ: "TXA Hub - chuong", "TXA Hub - onlol")
     */
    public final void addAllAppSoundsToMediaStore() {
    }
    
    /**
     * Thêm một file nhạc chuông vào MediaStore
     */
    private final android.net.Uri addSingleSoundToMediaStore(java.io.File soundFile, java.lang.String displayName) {
        return null;
    }
    
    /**
     * Thêm nhạc chuông của app vào MediaStore để xuất hiện trong RingtoneManager
     * @deprecated Sử dụng addAllAppSoundsToMediaStore() thay thế
     */
    @kotlin.Suppress(names = {"UNUSED_PARAMETER"})
    @org.jetbrains.annotations.Nullable
    @java.lang.Deprecated
    public final android.net.Uri addAppSoundToMediaStore(@org.jetbrains.annotations.Nullable
    android.net.Uri sourceUri, @org.jetbrains.annotations.NotNull
    java.lang.String displayName) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/txahub/app/utils/NotificationSoundManager$Companion;", "", "()V", "ALLOWED_MIME_TYPES", "", "", "CUSTOM_SOUND_FOLDER", "KEY_CUSTOM_SOUND_URI", "KEY_DEFAULT_APP_SOUND_URI", "KEY_SOUND_TYPE", "MAX_SOUND_DURATION_MS", "", "MAX_SOUND_SIZE_BYTES", "", "PREFS_NAME", "SOUND_FOLDER", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}