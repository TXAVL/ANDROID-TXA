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

**Version:** `2.2.4_txa` (Build: `125`)

## 📝 Lịch Sử Cập Nhật Gần Nhất

### Version 2.2.4_txa (125)

**Cải thiện:**
- Tăng độ ưu tiên thông báo cập nhật
  - Đổi channel importance từ `IMPORTANCE_HIGH` sang `IMPORTANCE_MAX`
  - Luôn hiển thị full screen intent (không chỉ force update)
  - Thêm `setWhen()` để hiển thị thời gian
  - Đảm bảo bypass DND luôn được bật

- Sửa lỗi TTS không đọc thông báo cập nhật
  - Thêm logging chi tiết cho TTS initialization và speak
  - Đảm bảo TTS được khởi tạo đúng cách trước khi đọc
  - Thêm retry logic nếu TTS chưa sẵn sàng
  - Đảm bảo TTS được gọi trên main thread

- Cập nhật Passkey API endpoints
  - Đổi từ dấu gạch dưới sang dấu gạch ngang (`create-challenge`, `verify-registration`, `verify-authentication`)
  - Phù hợp với API documentation mới nhất

- Cải thiện hệ thống thông báo
  - Background notification: `IMPORTANCE_LOW`, không làm phiền user
  - Update notification: `IMPORTANCE_MAX`, ưu tiên cao nhất

### Version 2.2.3_txa (124)

**Cải thiện:**
- Sửa logic cập nhật ngôn ngữ
  - Thêm `attachBaseContext()` trong SettingsActivity để áp dụng locale mới khi recreate
  - Restart toàn bộ app khi đổi ngôn ngữ để áp dụng cho tất cả activity
  - Đảm bảo ngôn ngữ được cập nhật đúng cách

- Thêm log sau khi đăng nhập thành công
  - Log thông tin user sau khi login thành công trong LoginActivity
  - Log dashboard data sau khi load thành công trong MainActivity

- Sửa Passkey trong Settings
  - Implement `registerPasskey()` trong SettingsActivity
  - Gọi API `create-challenge` với type "registration"
  - Sử dụng PasskeyManager để tạo Passkey

- Cải thiện Passkey log
  - Bỏ kiểm tra setting, luôn log vào file nếu có quyền ghi file
  - Thêm log chi tiết cho Passkey authentication flow

- Đảm bảo thông báo "app đang chạy nền"
  - Start UpdateCheckService trong SplashActivity khi chuyển màn hình
  - Service tạo foreground notification ngay khi start

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