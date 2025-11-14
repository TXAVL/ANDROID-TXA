# TXA Hub Mobile App - Deeplink API

Hệ thống deeplink cho ứng dụng Android TXA Hub Mobile, cho phép website và các ứng dụng khác mở trực tiếp các màn hình cụ thể trong app.

## 📋 Mục lục
- [Tổng quan](#tổng-quan)
- [Cấu trúc Deeplink](#cấu-trúc-deeplink)
- [Các Deeplink hỗ trợ](#các-deeplink-hỗ-trợ)
- [Cách sử dụng](#cách-sử-dụng)
- [Ví dụ triển khai](#ví-dụ-triển-khai)
- [Lưu ý](#lưu-ý)

## 🚀 Tổng quan

App TXA Hub Mobile hỗ trợ deeplink với scheme `txahub://` để mở các màn hình cụ thể trong ứng dụng. Điều này cho phép website hoặc các ứng dụng khác điều hướng người dùng đến các phần cụ thể trong app.

## 📐 Cấu trúc Deeplink

### Format
```
txahub://[path]
```

### Ví dụ
```
txahub://update
txahub://settings
txahub://logs
```

## 🔗 Các Deeplink hỗ trợ

### 1. `txahub://update`
**Mô tả:** Mở màn hình Settings và tự động scroll đến phần "Kiểm tra cập nhật".

**Hành vi:**
- Mở `SettingsActivity`
- Tự động scroll đến nút "Kiểm tra cập nhật"
- Focus vào nút để người dùng có thể bấm ngay

**Ví dụ:**
```html
<a href="txahub://update">Kiểm tra cập nhật</a>
```

**JavaScript:**
```javascript
window.location.href = 'txahub://update';
```

---

### 2. `txahub://settings`
**Mô tả:** Mở màn hình Settings của app.

**Hành vi:**
- Mở `SettingsActivity`
- Hiển thị toàn bộ cài đặt của app

**Ví dụ:**
```html
<a href="txahub://settings">Mở cài đặt</a>
```

**JavaScript:**
```javascript
window.location.href = 'txahub://settings';
```

---

### 3. `txahub://logs`
**Mô tả:** Mở màn hình xem logs của app.

**Hành vi:**
- Mở `LogViewerActivity`
- Hiển thị danh sách các file log
- Người dùng có thể xem, chia sẻ hoặc xóa logs

**Ví dụ:**
```html
<a href="txahub://logs">Xem logs</a>
```

**JavaScript:**
```javascript
window.location.href = 'txahub://logs';
```

---

### 4. `txahub://changelog`
**Mô tả:** Mở màn hình Changelog để xem các thay đổi của app.

**Hành vi:**
- Mở `ChangelogActivity`
- Hiển thị danh sách tất cả các phiên bản và changelog
- Phiên bản hiện tại được mở rộng mặc định

**Ví dụ:**
```html
<a href="txahub://changelog">Xem changelog</a>
```

**JavaScript:**
```javascript
window.location.href = 'txahub://changelog';
```

---

### 5. `txahub://dashboard`
**Mô tả:** Mở màn hình Dashboard (Bảng điều khiển chính).

**Hành vi:**
- Kiểm tra trạng thái đăng nhập
- Nếu đã đăng nhập: Mở `MainActivity` (Dashboard)
- Nếu chưa đăng nhập: Mở `LoginActivity`

**Ví dụ:**
```html
<a href="txahub://dashboard">Mở Dashboard</a>
```

**JavaScript:**
```javascript
window.location.href = 'txahub://dashboard';
```

---

## 💻 Cách sử dụng

### Trên Website (HTML)

#### Sử dụng thẻ `<a>`
```html
<a href="txahub://update">Kiểm tra cập nhật</a>
<a href="txahub://settings">Cài đặt</a>
<a href="txahub://logs">Xem logs</a>
```

#### Sử dụng JavaScript
```javascript
// Mở deeplink
function openDeepLink(path) {
    window.location.href = `txahub://${path}`;
}

// Ví dụ sử dụng
openDeepLink('update');
openDeepLink('settings');
```

#### Sử dụng button
```html
<button onclick="window.location.href='txahub://update'">
    Kiểm tra cập nhật
</button>
```

---

### Trên Android (Java/Kotlin)

```kotlin
val intent = Intent(Intent.ACTION_VIEW, Uri.parse("txahub://update"))
startActivity(intent)
```

---

### Trên iOS (Swift)

```swift
if let url = URL(string: "txahub://update") {
    UIApplication.shared.open(url)
}
```

---

## 📝 Ví dụ triển khai

### Ví dụ 1: Trang web với nút "Kiểm tra cập nhật"

```html
<!DOCTYPE html>
<html>
<head>
    <title>TXA Hub - Kiểm tra cập nhật</title>
</head>
<body>
    <h1>TXA Hub Mobile App</h1>
    <p>Nhấn vào nút bên dưới để mở app và kiểm tra cập nhật:</p>
    
    <button onclick="checkUpdate()" style="padding: 10px 20px; font-size: 16px;">
        Kiểm tra cập nhật trong app
    </button>
    
    <script>
        function checkUpdate() {
            // Thử mở app
            window.location.href = 'txahub://update';
            
            // Fallback: Nếu app chưa cài, có thể redirect đến trang download
            setTimeout(function() {
                // Kiểm tra xem app có mở không (tùy chọn)
                // Nếu không, có thể hiển thị thông báo hoặc redirect
            }, 1000);
        }
    </script>
</body>
</html>
```

---

### Ví dụ 2: Trang web với nhiều deeplink

```html
<!DOCTYPE html>
<html>
<head>
    <title>TXA Hub - Liên kết nhanh</title>
    <style>
        .deeplink-button {
            display: block;
            margin: 10px 0;
            padding: 15px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            text-align: center;
        }
        .deeplink-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>TXA Hub Mobile - Liên kết nhanh</h1>
    
    <a href="txahub://dashboard" class="deeplink-button">
        📊 Mở Dashboard
    </a>
    
    <a href="txahub://update" class="deeplink-button">
        🔄 Kiểm tra cập nhật
    </a>
    
    <a href="txahub://settings" class="deeplink-button">
        ⚙️ Cài đặt
    </a>
    
    <a href="txahub://logs" class="deeplink-button">
        📋 Xem logs
    </a>
    
    <a href="txahub://changelog" class="deeplink-button">
        📝 Xem changelog
    </a>
</body>
</html>
```

---

### Ví dụ 3: PHP redirect

```php
<?php
// Kiểm tra user agent để xác định thiết bị
$userAgent = $_SERVER['HTTP_USER_AGENT'];
$isAndroid = strpos($userAgent, 'Android') !== false;

if ($isAndroid) {
    // Redirect đến deeplink
    header('Location: txahub://update');
    exit;
} else {
    // Fallback cho các thiết bị khác
    header('Location: /download');
    exit;
}
?>
```

---

## ⚠️ Lưu ý

### 1. App chưa được cài đặt
- Nếu app chưa được cài đặt trên thiết bị, deeplink sẽ không hoạt động
- Nên có fallback để redirect đến trang download hoặc Play Store

### 2. Kiểm tra app có mở không
```javascript
let appOpened = false;
const startTime = Date.now();

window.location.href = 'txahub://update';

// Kiểm tra sau 1 giây
setTimeout(function() {
    if (Date.now() - startTime < 1000) {
        // App có thể đã mở
        appOpened = true;
    } else {
        // App có thể chưa được cài đặt
        // Redirect đến Play Store hoặc trang download
        window.location.href = 'https://play.google.com/store/apps/details?id=com.txahub.app';
    }
}, 1000);
```

### 3. Xử lý lỗi
- Luôn có fallback cho trường hợp app chưa cài đặt
- Có thể hiển thị thông báo cho người dùng

### 4. Bảo mật
- Deeplink không chứa thông tin nhạy cảm
- Không truyền token hoặc mật khẩu qua deeplink
- App sẽ kiểm tra quyền truy cập khi mở các màn hình

### 5. Testing
- Test trên thiết bị thật (không hoạt động trên emulator nếu app chưa cài)
- Test với app đã cài và chưa cài
- Test với các trình duyệt khác nhau

---

## 🔗 Liên kết

- Website: https://txahub.click
- X (Twitter): https://x.com/TxaVlog
- Facebook: fb.com/vlog.txa.2311
- YouTube: https://youtube.com/@admintxa

---

## 📄 License

Copyright © TXA Hub. All rights reserved.

