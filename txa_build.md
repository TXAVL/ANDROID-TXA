# TXA Hub Native App - Build Instructions & AI Prompt

## Tổng Quan

Đây là project Android Native App mới cho TXA Hub, **KHÔNG sử dụng WebView** như app cũ. App sẽ gọi trực tiếp các API từ web và hiển thị UI native hoàn toàn.

## Cấu Trúc Project

```
txa-hub-native-app/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/txahub/nativeapp/
│   │       │   ├── api/          # API client classes
│   │       │   ├── models/       # Data models
│   │       │   ├── ui/           # Activities, Fragments
│   │       │   ├── utils/        # Utility classes (copied from old app)
│   │       │   └── services/     # Background services
│   │       ├── res/              # Resources
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
├── gradle.properties
├── version.properties
├── txa_build_push.bat
└── txa-release-key.keystore
```

## Files Đã Copy Từ Project Cũ

Các file utility sau đã được copy và cần được sử dụng lại:

1. **NotificationHelper.kt** - Quản lý thông báo
2. **NotificationSoundManager.kt** - Quản lý nhạc chuông thông báo, thêm vào MediaStore
3. **NotificationTTSManager.kt** - Đọc thông báo bằng TTS
4. **UpdateChecker.kt** - Kiểm tra cập nhật
5. **UpdateCheckService.kt** - Service chạy nền kiểm tra cập nhật
6. **LogWriter.kt** - Ghi log vào file
7. **LogSettingsManager.kt** - Cài đặt bật/tắt log
8. **ChangelogActivity.kt** - Hiển thị changelog
9. **AndroidAutoGroupingManager.kt** - Quản lý nhóm thông báo Android Auto

## API Documentation

Xem file `API_DOCUMENTATION.md` (sẽ được tạo) hoặc tài liệu API đầy đủ đã được cung cấp.

**Base URL:** `https://txahub.click/api/` hoặc `https://txahub.click/admin-api/`

**Authentication:** Bearer Token trong header `Authorization: Bearer {API_KEY}`

## Yêu Cầu Chức Năng

### 1. Authentication & User Management

- **Login Screen:**
  - Form đăng nhập với email/password
  - Hỗ trợ Passkey authentication (nếu license cho phép)
  - Remember me
  - Link quên mật khẩu

- **Registration Screen:**
  - Form đăng ký với name, email, password
  - Email verification flow

- **User Profile:**
  - Hiển thị thông tin user từ `/api/user`
  - Hiển thị license info (TXA Pro/VIP) và restricted features
  - Cập nhật profile
  - Đổi mật khẩu
  - Quản lý Passkey (nếu license cho phép)

### 2. Links Management

- **Links List Screen:**
  - Hiển thị danh sách links từ `/api/links`
  - Pagination
  - Filter theo project
  - Search
  - Pull to refresh

- **Link Detail Screen:**
  - Thông tin chi tiết link
  - Statistics từ `/api/statistics/{link_id}`
  - Edit link
  - Delete link
  - Share link

- **Create/Edit Link Screen:**
  - Form tạo/sửa link
  - Chọn project
  - Chọn domain
  - Chọn pixels
  - Bulk create support

### 3. Projects Management

- **Projects List Screen:**
  - Danh sách projects từ `/api/projects`
  - Create/Edit/Delete project

### 4. Statistics & Analytics

- **Statistics Screen:**
  - Overview statistics
  - Charts (line, bar, pie)
  - Filter theo date range
  - Export data

### 5. Settings

- **Settings Screen:**
  - Notification settings
    - Bật/tắt thông báo
    - Chọn nhạc chuông (từ NotificationSoundManager)
    - Đọc thông báo (TTS)
    - Nhóm thông báo Android Auto
  - Log settings
    - Bật/tắt các loại log (API, App, Crash, Update Check, Passkey)
    - Xem log files
    - Share log files
  - App settings
    - Language
    - Theme (Light/Dark)
    - Auto-update check

### 6. Notifications

- **Update Notifications:**
  - Sử dụng `NotificationHelper` và `UpdateCheckService`
  - Hiển thị thông báo khi có bản cập nhật mới
  - TTS đọc thông báo (với suffix "APP ĐƯỢC BUILD BỞI TÊ ÍCH A")

- **Background Service:**
  - `UpdateCheckService` chạy foreground service
  - Tự động kiểm tra cập nhật định kỳ
  - Notification tự động hiện lại nếu bị dismiss

### 7. Changelog

- **Changelog Screen:**
  - Sử dụng `ChangelogActivity` logic
  - Hiển thị tất cả changelogs từ API
  - Expandable list
  - Mark version hiện tại

### 8. Logging System

- **Log Viewer:**
  - Xem tất cả log files
  - Filter theo loại (API, App, Crash, Update Check, Passkey)
  - Share log files
  - Clear old logs

- **Crash Logging:**
  - Tự động ghi crash log khi app crash
  - Sử dụng `LogWriter.writeCrashLog()`

## Technical Requirements

### Architecture

- **MVVM Pattern** với ViewModel và LiveData/Flow
- **Repository Pattern** cho data layer
- **Dependency Injection** (Hilt hoặc Koin)
- **Coroutines** cho async operations
- **Retrofit** cho API calls
- **Room Database** cho local caching (optional)

### Dependencies

```gradle
dependencies {
    // Core
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.10.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    
    // Lifecycle & ViewModel
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.6.2'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.2'
    
    // Navigation
    implementation 'androidx.navigation:navigation-fragment-ktx:2.7.5'
    implementation 'androidx.navigation:navigation-ui-ktx:2.7.5'
    
    // Networking
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.12.0'
    
    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
    
    // Dependency Injection
    implementation 'com.google.dagger:hilt-android:2.48'
    kapt 'com.google.dagger:hilt-compiler:2.48'
    
    // Image Loading
    implementation 'com.github.bumptech.glide:glide:4.16.0'
    
    // Charts (for statistics)
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
    
    // Passkey Support
    implementation 'androidx.credentials:credentials:1.2.2'
    implementation 'androidx.credentials:credentials-play-services-auth:1.2.2'
    implementation 'com.google.android.gms:play-services-auth:20.7.0'
    
    // Room Database (optional)
    implementation 'androidx.room:room-runtime:2.6.1'
    implementation 'androidx.room:room-ktx:2.6.1'
    kapt 'androidx.room:room-compiler:2.6.1'
}
```

### API Client Structure

```kotlin
// api/TXAApiService.kt
interface TXAApiService {
    @GET("user")
    suspend fun getUser(): Response<ApiResponse<User>>
    
    @GET("links")
    suspend fun getLinks(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 25
    ): Response<ApiResponse<List<Link>>>
    
    // ... more endpoints
}

// api/ApiClient.kt
class ApiClient {
    private val retrofit: Retrofit
    val apiService: TXAApiService
    
    fun setAuthToken(token: String)
    fun clearAuthToken()
}
```

### Models

Tạo data classes cho tất cả API responses:

```kotlin
// models/User.kt
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val license: License?,
    // ... other fields
)

data class License(
    val type: String,
    val is_pro: Boolean,
    val is_vip: Boolean,
    val restricted_features: List<String>
)

// models/Link.kt
data class Link(
    val id: Int,
    val url: String,
    val location_url: String,
    val clicks: Int,
    // ... other fields
)
```

### UI Screens

1. **SplashActivity** - Splash screen, check authentication
2. **LoginActivity** - Login/Registration
3. **MainActivity** - Main container với Bottom Navigation hoặc Drawer
4. **LinksFragment** - Danh sách links
5. **LinkDetailFragment** - Chi tiết link
6. **StatisticsFragment** - Thống kê
7. **ProjectsFragment** - Quản lý projects
8. **SettingsFragment** - Cài đặt
9. **ChangelogActivity** - Changelog (reuse from old app)
10. **LogViewerActivity** - Xem logs

## Build Instructions

1. **Setup:**
   ```bash
   cd txa-hub-native-app
   ./gradlew build
   ```

2. **Build Release:**
   ```bash
   ./txa_build_push.bat --release
   ```

3. **Version Management:**
   - Sử dụng `version.properties` như project cũ
   - Follow `txa_versioning_rules.md`

## Notes

- **License Restrictions:** Kiểm tra `license.restricted_features` trước khi hiển thị các tính năng bị chặn
- **Rate Limiting:** Handle 429 responses và hiển thị thông báo
- **Offline Support:** Cache data locally nếu cần
- **Error Handling:** Hiển thị error messages thân thiện
- **Loading States:** Hiển thị loading indicators khi fetch data
- **Empty States:** Hiển thị empty state khi không có data

## AI Prompt để Code App

Bạn là một Android developer chuyên nghiệp. Hãy tạo một Android Native App hoàn toàn mới cho TXA Hub với các yêu cầu sau:

1. **Không sử dụng WebView** - Tất cả UI phải là native Android (XML layouts, Compose, hoặc cả hai)

2. **API Integration:**
   - Sử dụng Retrofit để gọi các API endpoints từ tài liệu API đã cung cấp
   - Base URL: `https://txahub.click/api/`
   - Authentication: Bearer Token trong header
   - Handle rate limiting (429 responses)
   - Error handling đầy đủ

3. **Reuse Existing Utilities:**
   - Copy và sử dụng lại các file utility từ project cũ:
     - NotificationHelper, NotificationSoundManager, NotificationTTSManager
     - UpdateChecker, UpdateCheckService
     - LogWriter, LogSettingsManager
     - ChangelogActivity logic
   - Đảm bảo tất cả chức năng thông báo, logging, update check hoạt động như cũ

4. **UI/UX Requirements:**
   - Material Design 3
   - Dark mode support
   - Responsive design
   - Smooth animations
   - Pull to refresh
   - Swipe to delete (nếu phù hợp)

5. **Architecture:**
   - MVVM pattern
   - Repository pattern
   - Dependency Injection (Hilt recommended)
   - Coroutines + Flow/LiveData

6. **Key Features:**
   - Authentication (Login, Register, Passkey nếu license cho phép)
   - Links management (CRUD)
   - Projects management
   - Statistics với charts
   - Settings (notifications, logs, app settings)
   - Changelog viewer
   - Log viewer

7. **License Handling:**
   - Kiểm tra `license.restricted_features` từ `/api/user`
   - Ẩn/disable các tính năng bị chặn (plugins, cronjob, custom_images, plan, file_extensions, passkey, google_drive)
   - Chỉ hiển thị tính năng khi `is_vip = true` hoặc tính năng không có trong `restricted_features`

8. **Notifications:**
   - Update notifications với sound (từ NotificationSoundManager)
   - TTS đọc thông báo (với suffix "APP ĐƯỢC BUILD BỞI TÊ ÍCH A")
   - Background service tự động check updates
   - Notification tự động hiện lại nếu bị dismiss

9. **Logging:**
   - Ghi log vào file (API, App, Crash, Update Check, Passkey)
   - Settings để bật/tắt từng loại log
   - Log viewer để xem và share logs

10. **Versioning:**
    - Sử dụng `version.properties` và `txa_versioning_rules.md`
    - Auto-increment version code

Hãy bắt đầu code từng phần một, bắt đầu với:
1. Project setup (Gradle, dependencies, manifest)
2. API client và models
3. Authentication flow
4. Main screens (Links, Projects, Statistics, Settings)
5. Integrate utilities (notifications, logging, update check)

Code phải clean, well-documented, và follow Android best practices.

