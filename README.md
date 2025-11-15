# TXA Hub Native App

Android Native App cho TXA Hub - **KHÔNG sử dụng WebView**, gọi trực tiếp API và hiển thị UI native.

## 📱 Chức Năng Chính

- ✅ **Authentication**: Đăng nhập, Đăng ký, Passkey (WebAuthn/FIDO2)
- ✅ **Dashboard**: Quản lý Links, Projects, Statistics
- ✅ **Admin Management**: Quản lý người dùng và quyền (dành cho admin)
- ✅ **Notifications**: Thông báo cập nhật, tùy chỉnh âm thanh, TTS
- ✅ **Background Service**: Chạy nền kiểm tra cập nhật tự động
- ✅ **Logging**: Ghi log API, app, crash vào file
- ✅ **Changelog**: Hiển thị lịch sử cập nhật
- ✅ **Settings**: Cài đặt ngôn ngữ, thông báo, logs
- ✅ **Deeplink**: Hỗ trợ deeplink `txahub://`

## 📦 Version Hiện Tại

**Version:** `2.2.2_txa` (Build: `123`)

## 📝 Lịch Sử Cập Nhật Gần Nhất

### Version 2.2.2_txa (123)

**Sửa lỗi:**
- Sửa lỗi crash tất cả Activity khi dùng Toolbar
  - Thêm theme NoActionBar cho tất cả Activity dùng Toolbar
  - Sửa lỗi "This Activity already has an action bar supplied by the window decor"
- Sửa lỗi hiển thị changelog modal
  - Hiển thị changelog modal cho phiên bản hiện tại ngay lần đầu vào app
  - Sử dụng ChangelogDialog (modal) thay vì ChangelogActivity
  - Changelog modal hiển thị sau khi cấp quyền xong, trước khi vào Login/Main
- Sửa lỗi build
  - Sửa string resource và import statements

## 🔧 Build

```bash
# Build debug
./gradlew assembleDebug

# Build release
./txa_build_push.bat --release
```

## 📚 Tài Liệu

- **version.properties** - Quản lý version và changelog
- **txa_build.md** - Hướng dẫn build và AI prompt chi tiết


## 📥 Tải App

Tải app tại: [GitHub Releases](https://github.com/TXAVL/ANDROID-TXA/releases)

Hoặc xem folder releases: [releases/](https://github.com/TXAVL/ANDROID-TXA/tree/main/releases)