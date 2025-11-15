# Yêu Cầu Cập Nhật Passkey API - verify_authentication

## 📋 Vấn Đề Hiện Tại

Hiện tại, endpoint `POST /passkey-api/verify_authentication` chỉ trả về:

```json
{
    "success": true,
    "message": "Authentication successful"
}
```

## ❌ Vấn Đề

Mobile app cần **token (api_key)** để gọi các API khác sau khi đăng nhập bằng Passkey. Nếu không có token trong response, app không thể:
- Gọi API `/api/user` để lấy thông tin user (vì cần token)
- Gọi các API khác yêu cầu authentication
- Lưu token để sử dụng cho các request tiếp theo

## ✅ Yêu Cầu

### 1. Cập Nhật Response của `verify_authentication`

**Endpoint:** `POST /passkey-api/verify_authentication`

**Response hiện tại:**
```json
{
    "success": true,
    "message": "Authentication successful"
}
```

**Response mong muốn:**
```json
{
    "success": true,
    "message": "Authentication successful",
    "data": {
        "token": "user_api_key_here",
        "user": {
            "id": 1,
            "name": "John Doe",
            "email": "user@example.com",
            "email_verified_at": "2025-01-01 12:00:00",
            "is_admin": false,
            "api_key": "user_api_key_here",
            "license": {
                "type": "free",
                "is_pro": false,
                "is_vip": false,
                "restricted_features": []
            }
        }
    }
}
```

**Hoặc đơn giản hơn (chỉ token và user cơ bản):**
```json
{
    "success": true,
    "message": "Authentication successful",
    "data": {
        "token": "user_api_key_here",
        "user": {
            "id": 1,
            "name": "John Doe",
            "email": "user@example.com",
            "email_verified_at": "2025-01-01 12:00:00",
            "is_admin": false
        }
    }
}
```

### 2. Format Response

Response nên tuân theo format JSON:API như các endpoint khác:

```json
{
    "success": true,
    "message": "Authentication successful",
    "data": {
        "token": "...",
        "user": { ... }
    }
}
```

**Lưu ý:**
- `token` là `api_key` từ bảng `users` của user đã xác thực
- `user` là thông tin user đầy đủ (giống như response của `/auth/login`)
- Format giống với `AuthResponse` trong `/auth/login` để app có thể xử lý đồng nhất

### 3. Tương Thích Ngược

Nếu không thể thêm `data` vào response ngay, có thể:
- Thêm field `token` riêng (nhưng không khuyến khích)
- Hoặc giữ nguyên và app sẽ xử lý fallback

**Khuyến nghị:** Thêm `data` field với format giống `/auth/login` để nhất quán.

## 📝 Lý Do

1. **Consistency:** Các endpoint đăng nhập khác (`/auth/login`, `/auth/register`) đều trả về token và user info
2. **User Experience:** User không cần đăng nhập lại sau khi xác thực Passkey thành công
3. **Security:** Token được trả về ngay sau khi xác thực, không cần gọi thêm API
4. **Simplicity:** App không cần xử lý logic phức tạp để lấy token

## 🔄 Flow Mong Muốn

1. User chọn đăng nhập bằng Passkey
2. App gọi `POST /passkey-api/create_challenge` với `type=authentication`
3. App gọi WebAuthn API để lấy credential
4. App gọi `POST /passkey-api/verify_authentication` với credential
5. **Server trả về token và user info trong response** ← CẦN THÊM
6. App lưu token và user info, chuyển đến màn hình chính
7. User đã đăng nhập thành công, không cần thao tác thêm

## 📌 Priority

**HIGH** - Tính năng này cần thiết để Passkey hoạt động đúng trong mobile app.

## ✅ Checklist

- [ ] Cập nhật `verify_authentication` endpoint để trả về `data` với `token` và `user`
- [ ] Đảm bảo `token` là `api_key` từ bảng `users`
- [ ] Đảm bảo format response giống với `/auth/login` để nhất quán
- [ ] Test với mobile app để đảm bảo hoạt động đúng
- [ ] Cập nhật API documentation nếu có

---

**Ngày tạo:** 2025-01-XX  
**Người yêu cầu:** Mobile App Development Team  
**Priority:** HIGH

