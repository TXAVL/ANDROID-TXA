# API Endpoints Cần Bổ Sung Cho Web Server

Dựa trên tài liệu API hiện tại và yêu cầu của ứng dụng Android TXAHUB-SOCIAL, web server cần **bổ sung các JSON API endpoints** sau đây:

**Base URL:** `https://txahub.click/api/`

**Content-Type:** `application/json`

**Accept:** `application/json`

---

## 📋 Tổng Quan

**Vấn đề:** Hiện tại web server chỉ có các endpoints **form-based** (POST form data) cho authentication, nhưng app Android cần **JSON API** để gọi trực tiếp.

**Giải pháp:** Tạo thêm các JSON API endpoints mới song song với các form-based endpoints hiện có.

---

## 🔐 1. Authentication JSON API Endpoints

### 1.1. POST `/api/auth/login` ⚠️ **CẦN TẠO MỚI**

**Hiện tại:** Web server chỉ có `POST /login` (form-based)

**Cần tạo:** JSON API endpoint mới

**Request:**
```json
POST /api/auth/login
Content-Type: application/json

{
    "email": "user@example.com",
    "password": "password123"
}
```

**Response Success (200):**
```json
{
    "success": true,
    "data": {
        "token": "api_key_from_user_table",
        "user": {
            "id": 1,
            "name": "John Doe",
            "email": "user@example.com",
            "email_verified_at": "2025-01-01 12:00:00",
            "is_admin": false,
            "license": {
                "type": "TXA Pro License",
                "is_pro": true,
                "is_vip": false,
                "restricted_features": ["plugins", "cronjob", ...]
            }
        }
    },
    "message": "Đăng nhập thành công"
}
```

**Lưu ý:**
- Token trả về là `api_key` từ bảng `users` (đã có sẵn trong `/api/user`)
- Sau khi login thành công, tạo session (nếu cần) hoặc chỉ trả về token
- Response format theo JSON:API specification như các API khác

---

### 1.2. POST `/api/auth/register` ⚠️ **CẦN TẠO MỚI**

**Hiện tại:** Web server chỉ có `POST /register` (form-based)

**Cần tạo:** JSON API endpoint mới

**Request:**
```json
POST /api/auth/register
Content-Type: application/json

{
    "name": "John Doe",
    "email": "user@example.com",
    "password": "password123",
    "password_confirmation": "password123"
}
```

**Response Success (200):**
```json
{
    "success": true,
    "data": {
        "token": "api_key_from_user_table",
        "user": {
            "id": 1,
            "name": "John Doe",
            "email": "user@example.com",
            "email_verified_at": null,
            "is_admin": false,
            "license": null
        }
    },
    "message": "Đăng ký thành công. Vui lòng xác minh email."
}
```

**Lưu ý:**
- Sau khi đăng ký, tự động gửi email xác minh (giống như form-based endpoint)
- Trả về token ngay để user có thể sử dụng app ngay lập tức

---

### 1.3. POST `/api/auth/verify-email` ⚠️ **CẦN TẠO MỚI**

**Hiện tại:** Web server chỉ có `GET /activate-user?email=...&email_activation_code=...` (web redirect)

**Cần tạo:** JSON API endpoint mới

**Request:**
```json
POST /api/auth/verify-email
Content-Type: application/json

{
    "email": "user@example.com",
    "token": "email_activation_code"
}
```

**Response Success (200):**
```json
{
    "success": true,
    "message": "Email đã được xác minh thành công"
}
```

**Response Error (400):**
```json
{
    "success": false,
    "message": "Token xác minh không hợp lệ hoặc đã hết hạn"
}
```

**Lưu ý:**
- Token là `email_activation_code` từ database
- Có thể sử dụng logic tương tự như `GET /activate-user` nhưng trả về JSON thay vì redirect

---

### 1.4. POST `/api/auth/resend-verification` ⚠️ **CẦN TẠO MỚI**

**Hiện tại:** Web server chỉ có `POST /resend-activation` (form-based)

**Cần tạo:** JSON API endpoint mới

**Request:**
```json
POST /api/auth/resend-verification
Content-Type: application/json

{
    "email": "user@example.com"
}
```

**Response Success (200):**
```json
{
    "success": true,
    "message": "Email xác minh đã được gửi lại"
}
```

**Response Error (404/422):**
```json
{
    "success": false,
    "message": "Email không tồn tại hoặc đã được xác minh"
}
```

**Lưu ý:**
- Có thể sử dụng logic tương tự như `POST /resend-activation`
- Cần rate limiting (ví dụ: 3 lần/giờ)

---

### 1.5. POST `/api/auth/forgot-password` ⚠️ **CẦN TẠO MỚI**

**Hiện tại:** Web server chỉ có `POST /lost-password` (form-based)

**Cần tạo:** JSON API endpoint mới

**Request:**
```json
POST /api/auth/forgot-password
Content-Type: application/json

{
    "email": "user@example.com"
}
```

**Response Success (200):**
```json
{
    "success": true,
    "message": "Email đặt lại mật khẩu đã được gửi"
}
```

**Response Error (404/422):**
```json
{
    "success": false,
    "message": "Email không tồn tại"
}
```

**Lưu ý:**
- Có thể sử dụng logic tương tự như `POST /lost-password`
- Gửi email với link reset password (format: `/reset-password/{md5_email}/{lost_password_code}`)
- Cần rate limiting (ví dụ: 3 lần/giờ)

---

### 1.6. POST `/api/auth/reset-password` ⚠️ **CẦN TẠO MỚI**

**Hiện tại:** Web server chỉ có `POST /reset-password/{md5_email}/{lost_password_code}` (form-based)

**Cần tạo:** JSON API endpoint mới

**Request:**
```json
POST /api/auth/reset-password
Content-Type: application/json

{
    "email": "user@example.com",
    "token": "lost_password_code",
    "password": "newpassword123",
    "password_confirmation": "newpassword123"
}
```

**Response Success (200):**
```json
{
    "success": true,
    "message": "Mật khẩu đã được đặt lại thành công"
}
```

**Response Error (400/422):**
```json
{
    "success": false,
    "message": "Token không hợp lệ hoặc đã hết hạn",
    "errors": {
        "password": ["Mật khẩu phải có ít nhất 6 ký tự"],
        "password_confirmation": ["Mật khẩu xác nhận không khớp"]
    }
}
```

**Lưu ý:**
- Token là `lost_password_code` từ database
- Có thể sử dụng logic tương tự như `POST /reset-password/{md5_email}/{lost_password_code}`
- Không cần MD5 hash email trong request body, chỉ cần email và token

---

### 1.7. POST `/api/auth/logout` ⚠️ **CẦN TẠO MỚI**

**Hiện tại:** Web server chưa có endpoint logout (có thể chỉ có session logout)

**Cần tạo:** JSON API endpoint mới

**Request:**
```json
POST /api/auth/logout
Authorization: Bearer {token}
Content-Type: application/json
```

**Response Success (200):**
```json
{
    "success": true,
    "message": "Đăng xuất thành công"
}
```

**Lưu ý:**
- Có thể chỉ cần validate token và trả về success
- Nếu có session, có thể xóa session
- Token vẫn có thể sử dụng lại (stateless) hoặc invalidate token (tùy implementation)

---

## 📊 2. Statistics JSON API Endpoints

### 2.1. GET `/api/statistics/user` ⚠️ **CẦN TẠO MỚI**

**Hiện tại:** Web server chỉ có `GET /api/statistics/{link_id}` (thống kê theo link)

**Cần tạo:** Endpoint mới để lấy tổng thống kê của user

**Request:**
```json
GET /api/statistics/user
Authorization: Bearer {token}
```

**Response Success (200):**
```json
{
    "success": true,
    "data": {
        "total_clicks": 1250,
        "total_links": 45,
        "total_projects": 8,
        "clicks_today": 25,
        "clicks_this_week": 150,
        "clicks_this_month": 450,
        "top_links": [
            {
                "id": 1,
                "url": "example",
                "location_url": "https://example.com",
                "clicks": 500
            },
            {
                "id": 2,
                "url": "example2",
                "location_url": "https://example2.com",
                "clicks": 300
            }
        ]
    }
}
```

**Logic tính toán:**
- `total_clicks`: Tổng số clicks của tất cả links của user
- `total_links`: Số lượng links của user (từ `GET /api/links`)
- `total_projects`: Số lượng projects của user (từ `GET /api/projects`)
- `clicks_today`: Tổng clicks trong ngày hôm nay
- `clicks_this_week`: Tổng clicks trong tuần này
- `clicks_this_month`: Tổng clicks trong tháng này
- `top_links`: Top 5-10 links có nhiều clicks nhất

**Lưu ý:**
- Có thể query từ bảng `track_links` hoặc bảng clicks tương ứng
- Cần join với bảng `links` để lấy thông tin link

---

## ✅ 3. Endpoints Đã Có Sẵn (Không Cần Tạo Mới)

### 3.1. GET `/api/user` ✅ **ĐÃ CÓ**

Endpoint này đã có sẵn và trả về đầy đủ thông tin user, bao gồm:
- Thông tin user cơ bản
- License info (is_pro, is_vip, restricted_features)
- Các thông tin khác

**Response format đã đúng:** JSON:API format với `data` object

---

## 🔄 4. So Sánh Form-based vs JSON API

| Chức năng | Form-based (Hiện có) | JSON API (Cần tạo) |
|-----------|---------------------|-------------------|
| Login | `POST /login` | `POST /api/auth/login` |
| Register | `POST /register` | `POST /api/auth/register` |
| Verify Email | `GET /activate-user?email=...&code=...` | `POST /api/auth/verify-email` |
| Resend Verification | `POST /resend-activation` | `POST /api/auth/resend-verification` |
| Forgot Password | `POST /lost-password` | `POST /api/auth/forgot-password` |
| Reset Password | `POST /reset-password/{md5}/{code}` | `POST /api/auth/reset-password` |
| Logout | Session logout | `POST /api/auth/logout` |
| Get User | `GET /api/user` ✅ | `GET /api/user` ✅ |
| User Statistics | Không có | `GET /api/statistics/user` ⚠️ |

---

## 📝 5. Response Format

Tất cả các JSON API endpoints mới cần tuân theo **JSON:API specification** giống như các API hiện có:

**Success Response:**
```json
{
    "success": true,
    "data": { ... },
    "message": "..." // optional
}
```

**Error Response:**
```json
{
    "success": false,
    "message": "Error message",
    "errors": {
        "field_name": ["Error detail 1", "Error detail 2"]
    }
}
```

---

## 🔒 6. Authentication

- **Login/Register:** Không cần Bearer token
- **Verify Email/Resend/Forgot Password:** Không cần Bearer token
- **Reset Password:** Không cần Bearer token (có token trong request body)
- **Logout/Get User/Statistics:** Cần Bearer token trong header

**Header:**
```
Authorization: Bearer {api_key}
```

---

## ⚡ 7. Rate Limiting

Nên implement rate limiting cho các endpoints mới:
- **Login:** Tối đa 5 lần/phút
- **Register:** Tối đa 3 lần/phút
- **Resend Verification:** Tối đa 3 lần/giờ
- **Forgot Password:** Tối đa 3 lần/giờ

Khi vượt quá rate limit, trả về status `429`:
```json
{
    "success": false,
    "message": "Quá nhiều yêu cầu. Vui lòng thử lại sau."
}
```

---

## 🎯 8. Tóm Tắt

**Cần tạo mới 8 endpoints:**

1. ✅ `POST /api/auth/login` - JSON API
2. ✅ `POST /api/auth/register` - JSON API
3. ✅ `POST /api/auth/verify-email` - JSON API
4. ✅ `POST /api/auth/resend-verification` - JSON API
5. ✅ `POST /api/auth/forgot-password` - JSON API
6. ✅ `POST /api/auth/reset-password` - JSON API
7. ✅ `POST /api/auth/logout` - JSON API
8. ✅ `GET /api/statistics/user` - JSON API

**Đã có sẵn:**
- ✅ `GET /api/user` - Đã có và đúng format

---

## 📌 Lưu Ý Quan Trọng

1. **Tương thích ngược:** Các form-based endpoints hiện có vẫn hoạt động bình thường, không ảnh hưởng đến web interface

2. **Reuse logic:** Có thể tái sử dụng logic từ form-based endpoints, chỉ cần thay đổi:
   - Input: JSON body thay vì form data
   - Output: JSON response thay vì redirect/HTML

3. **Token:** Sử dụng `api_key` từ bảng `users` làm Bearer token (đã có sẵn)

4. **Email:** Các email templates hiện có có thể tái sử dụng, chỉ cần đảm bảo link trong email có thể dùng cho cả web và mobile app

5. **Security:** 
   - Validate input giống như form-based endpoints
   - Rate limiting
   - CSRF protection không cần thiết cho JSON API (không có session)

---

**Liên hệ:** Nếu có thắc mắc về implementation, vui lòng liên hệ team phát triển.

