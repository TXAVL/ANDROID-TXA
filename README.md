# TXA Hub Native App

Android Native App mới cho TXA Hub - **KHÔNG sử dụng WebView**, gọi trực tiếp API và hiển thị UI native.

## 📁 Cấu Trúc Project

```
txa-hub-native-app/
├── app/
│   └── src/main/
│       ├── java/com/txahub/nativeapp/
│       │   └── utils/          # Utility classes (đã copy từ project cũ)
│       └── res/                 # Resources (sẽ tạo)
├── build.gradle                 # Root build file
├── settings.gradle              # Project settings
├── gradle.properties           # Gradle properties
├── version.properties          # Version management
├── txa_build_push.bat          # Build script
├── txa-release-key.keystore     # Signing key
├── txa_build.md                 # ⭐ HƯỚNG DẪN BUILD VÀ AI PROMPT
└── txa_api_endpoints_can_bo_sung.md  # API endpoints cần bổ sung cho web server
```

## 🚀 Bắt Đầu

1. **Đọc `txa_build.md`** - File này chứa:
   - Hướng dẫn build project
   - AI prompt để code app
   - Yêu cầu chức năng chi tiết
   - Technical requirements

2. **Xem API Documentation** - File `txa_api_endpoints_can_bo_sung.md` mô tả các API endpoints cần bổ sung cho web server

3. **Files Đã Copy:**
   - `NotificationHelper.kt` - Quản lý thông báo
   - `NotificationSoundManager.kt` - Quản lý nhạc chuông, thêm vào MediaStore
   - `NotificationTTSManager.kt` - Đọc thông báo bằng TTS
   - `UpdateChecker.kt` - Kiểm tra cập nhật
   - `UpdateCheckService.kt` - Service chạy nền
   - `LogWriter.kt` - Ghi log vào file
   - `LogSettingsManager.kt` - Cài đặt bật/tắt log
   - `ChangelogActivity.kt` - Hiển thị changelog
   - `AndroidAutoGroupingManager.kt` - Quản lý nhóm thông báo

## 📋 Yêu Cầu Chính

- ✅ Native Android UI (không WebView)
- ✅ API Integration với Retrofit
- ✅ MVVM Architecture
- ✅ Authentication (Login, Register, Passkey)
- ✅ Links Management (CRUD)
- ✅ Projects Management
- ✅ Statistics với charts
- ✅ Settings (notifications, logs)
- ✅ Changelog viewer
- ✅ Log viewer
- ✅ Update notifications
- ✅ Background service

## 🔧 Build

```bash
# Build debug
./gradlew assembleDebug

# Build release
./txa_build_push.bat --release
```

## 📝 Versioning

Sử dụng `version.properties` và follow `txa_versioning_rules.md` (đã copy từ project cũ).

## 📚 Tài Liệu

- **txa_build.md** - Hướng dẫn build và AI prompt chi tiết
- **txa_api_endpoints_can_bo_sung.md** - API endpoints cần bổ sung cho web server
- **txa_versioning_rules.md** - Quy tắc versioning

## 🎯 Next Steps

1. Setup project structure (Gradle, dependencies, manifest)
2. Tạo API client và models
3. Implement authentication flow
4. Tạo main screens (Links, Projects, Statistics, Settings)
5. Integrate utilities (notifications, logging, update check)

Xem `txa_build.md` để biết chi tiết!

