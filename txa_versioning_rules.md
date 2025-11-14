# Quy tắc Versioning cho TXA Hub Mobile App

## Semver Format: `MAJOR.MINOR.PATCH_SUFFIX`

Ví dụ: `1.0.0_txa`, `1.1.0_txa`, `2.0.0_txa`

## Quy tắc tăng phiên bản

### 1. Fix lỗi / Fix bug (PATCH)
**Tăng số PATCH (số cuối cùng)**

- `1.0.0_txa` → `1.0.1_txa`
- `1.1.0_txa` → `1.1.1_txa`
- `2.0.0_txa` → `2.0.1_txa`

**Khi nào dùng:**
- Sửa lỗi crash
- Sửa lỗi UI nhỏ
- Sửa lỗi logic
- Sửa lỗi performance nhỏ
- Hotfix

**Ví dụ:**
- Fix lỗi crash khi mở app
- Fix lỗi hiển thị notification
- Fix lỗi deep link không hoạt động

---

### 2. Thêm tính năng mới (MAJOR)
**Tăng số MAJOR (số đầu tiên), reset MINOR và PATCH về 0**

- `1.0.0_txa` → `2.0.0_txa`
- `1.5.3_txa` → `2.0.0_txa`
- `2.1.0_txa` → `3.0.0_txa`

**Khi nào dùng:**
- Thêm tính năng lớn, quan trọng
- Thêm module mới hoàn toàn
- Thay đổi kiến trúc lớn
- Breaking changes (thay đổi API, database schema)

**Ví dụ:**
- Thêm tính năng SpeedTest
- Thêm tính năng đăng nhập OAuth
- Thêm tính năng chat real-time
- Thêm tính năng offline mode

---

### 3. Cập nhật tính năng (MINOR)
**Tăng số MINOR (số giữa), reset PATCH về 0**

- `1.0.0_txa` → `1.1.0_txa`
- `1.0.5_txa` → `1.1.0_txa`
- `1.5.0_txa` → `1.6.0_txa`

**Khi nào dùng:**
- Cải thiện tính năng hiện có
- Thêm tính năng nhỏ vào module có sẵn
- Cải thiện UX/UI
- Thêm tùy chọn mới (không breaking)

**Ví dụ:**
- Cải thiện giao diện Settings
- Thêm dark mode
- Thêm tùy chọn notification
- Cải thiện tốc độ load trang
- Thêm shortcut mới

---

## Tóm tắt

| Loại thay đổi | Tăng version | Ví dụ |
|---------------|--------------|-------|
| **Fix bug** | PATCH | `1.0.0` → `1.0.1` |
| **Tính năng mới (lớn)** | MAJOR | `1.0.0` → `2.0.0` |
| **Cập nhật tính năng** | MINOR | `1.0.0` → `1.1.0` |

## Lưu ý

1. **Version Code (versionCode):** Luôn tăng dần, không được trùng
   - Mỗi lần release, tăng versionCode lên 1
   - Không liên quan đến version name

2. **Version Name (versionName):** Theo quy tắc trên
   - Format: `MAJOR.MINOR.PATCH_SUFFIX`
   - Suffix: `_txa` (giữ nguyên)

3. **Khi không chắc:**
   - Nếu thay đổi nhỏ → PATCH
   - Nếu thay đổi vừa → MINOR
   - Nếu thay đổi lớn → MAJOR

---

## Lưu ý về Changelog

**QUAN TRỌNG:** Sau mỗi lần nâng phiên bản, cần ghi changelog vào tag code nhưng không ghi ra file riêng.

**Format changelog:**
```properties
# ========================================
# CHANGELOG - Version X.Y.Z_txa (versionCode)
# ========================================
# Tính năng mới:
# - Mô tả tính năng 1
# - Mô tả tính năng 2
# Cải thiện:
# - Mô tả cải thiện 1
# Sửa lỗi:
# - Mô tả lỗi đã sửa 1
# ========================================
```

**File này để tham khảo khi quyết định tăng phiên bản.**

