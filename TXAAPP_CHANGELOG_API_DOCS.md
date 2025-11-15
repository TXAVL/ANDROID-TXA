# TXA App Changelog API Documentation

## Tổng quan

API này được sử dụng để quản lý thông tin version và changelog cho ứng dụng TXA App. API này có format response giống hệt với API `txahubapp`, nhưng sử dụng endpoint `txaapp` thay vì `txahubapp`.

## Base URL

```
https://software.txahub.click/product/txaapp
```

## Endpoints

### 1. Lấy thông tin version mới nhất

**Endpoint:** `/latest` hoặc `/lastest` (hỗ trợ cả hai để tương thích)

**Method:** `GET`

**URL đầy đủ:**
- `https://software.txahub.click/product/txaapp/latest`
- `https://software.txahub.click/product/txaapp/lastest` (typo - để tương thích)

**Response Format:**

```json
{
  "version_name": "2.0.0_txa",
  "version_code": 101,
  "release_date": "2024-01-15",
  "changelog": "<h2>Version 2.0.0_txa</h2><ul><li>Thêm tính năng Passkey</li><li>Cải thiện hiệu suất</li></ul>",
  "download_url": "https://software.txahub.click/product/txaapp/download/2.0.0_txa.apk",
  "force_update": false
}
```

**Response Fields:**

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `version_name` | string | Yes | Tên version (ví dụ: "2.0.0_txa") |
| `version_code` | integer | Yes | Mã version (ví dụ: 101) |
| `release_date` | string | Yes | Ngày phát hành (format: "YYYY-MM-DD") |
| `changelog` | string | Yes | Nội dung changelog (HTML format) |
| `download_url` | string | Yes | URL để tải APK |
| `force_update` | boolean | No | Có bắt buộc cập nhật không (mặc định: false) |

**Error Response:**

```json
{
  "error": "Not found",
  "message": "Version not found"
}
```

**HTTP Status Codes:**
- `200 OK`: Thành công
- `404 Not Found`: Không tìm thấy version
- `500 Internal Server Error`: Lỗi server

---

### 2. Lấy tất cả changelog của mọi phiên bản

**Endpoint:** `/changelogs`

**Method:** `GET`

**URL đầy đủ:**
```
https://software.txahub.click/product/txaapp/changelogs
```

**Response Format:**

Trả về một JSON array chứa tất cả các version:

```json
[
  {
    "version_name": "2.0.0_txa",
    "version_code": 101,
    "release_date": "2024-01-15",
    "changelog": "<h2>Version 2.0.0_txa</h2><ul><li>Thêm tính năng Passkey</li><li>Cải thiện hiệu suất</li></ul>"
  },
  {
    "version_name": "1.0.0_txa",
    "version_code": 1,
    "release_date": "2024-01-01",
    "changelog": "<h2>Version 1.0.0_txa</h2><ul><li>Phiên bản đầu tiên</li></ul>"
  }
]
```

**Response Fields (mỗi item trong array):**

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `version_name` | string | Yes | Tên version |
| `version_code` | integer | Yes | Mã version |
| `release_date` | string | Yes | Ngày phát hành (format: "YYYY-MM-DD") |
| `changelog` | string | Yes | Nội dung changelog (HTML format) |

**Lưu ý:**
- Array được sắp xếp theo `version_code` giảm dần (version mới nhất trước)
- Nếu không có version nào, trả về mảng rỗng `[]`

**Error Response:**

```json
{
  "error": "Internal server error",
  "message": "Failed to fetch changelogs"
}
```

**HTTP Status Codes:**
- `200 OK`: Thành công
- `500 Internal Server Error`: Lỗi server

---

## Format Changelog (HTML)

Changelog được trả về dưới dạng HTML để hiển thị trong WebView. Có thể sử dụng các thẻ HTML cơ bản:

```html
<h2>Version 2.0.0_txa</h2>
<ul>
  <li>Thêm tính năng Passkey</li>
  <li>Cải thiện hiệu suất</li>
  <li>Sửa lỗi crash khi khởi động</li>
</ul>
<p>Một số cải tiến khác...</p>
```

---

## Yêu cầu Implementation

### 1. Giao diện quản lý

Cần tạo một giao diện mới để:
- Tạo version mới
- Chỉnh sửa thông tin version
- Xóa version (nếu cần)
- Quản lý changelog cho từng version

### 2. Database Schema

Đề xuất schema:

```sql
CREATE TABLE versions_table2 (
  id INT PRIMARY KEY AUTO_INCREMENT,
  version_name VARCHAR(50) UNIQUE NOT NULL,
  version_code INT UNIQUE NOT NULL,
  release_date DATE NOT NULL,
  changelog TEXT NOT NULL,
  download_url VARCHAR(500) NOT NULL,
  force_update BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### 3. API Endpoints cần implement

#### 3.1. Tạo version mới (Admin)

**Endpoint:** `POST /product/txaapp/versions`

**Request Body:**
```json
{
  "version_name": "2.0.0_txa",
  "version_code": 101,
  "release_date": "2024-01-15",
  "changelog": "<h2>Version 2.0.0_txa</h2><ul><li>Thêm tính năng Passkey</li></ul>",
  "download_url": "https://software.txahub.click/product/txaapp/download/2.0.0_txa.apk",
  "force_update": false
}
```

#### 3.2. Cập nhật version (Admin)

**Endpoint:** `PUT /product/txaapp/versions/{version_code}`

**Request Body:** (tương tự như tạo mới)

#### 3.3. Xóa version (Admin)

**Endpoint:** `DELETE /product/txaapp/versions/{version_code}`

#### 3.4. Lấy danh sách versions (Admin)

**Endpoint:** `GET /product/txaapp/versions`

**Response:** JSON array tương tự `/changelogs` nhưng có thêm các trường admin

---

## Testing

### Test với cURL

**Lấy version mới nhất:**
```bash
curl https://software.txahub.click/product/txaapp/latest
```

**Lấy tất cả changelog:**
```bash
curl https://software.txahub.click/product/txaapp/changelogs
```

---

## Notes

- API này được thiết kế để tương thích hoàn toàn với format của `txahubapp`
- Client app đã được cập nhật để sử dụng endpoint `txaapp` thay vì `txahubapp`
- Hỗ trợ cả `/latest` và `/lastest` để tương thích với code hiện tại
- Nâng cấp giao diện bên admin!