# Báo Cáo Lỗi Build Android Kotlin - TXA Hub Mobile App

## Tổng Quan
Dự án Android Kotlin gặp nhiều lỗi compile khi build release APK. Dưới đây là danh sách đầy đủ các lỗi và cách sửa.

---

## 1. LỖI: TXAApplication.kt - Return Type Mismatch

**Lỗi:**
```
Return type of 'attachBaseContext' is not a subtype of the return type of the overridden member
```

**Nguyên nhân:**
- Method `attachBaseContext` đang return `Context` nhưng base class yêu cầu `Unit`

**Cách sửa:**
```kotlin
// TRƯỚC (SAI):
override fun attachBaseContext(base: Context): Context {
    // ...
    return LocaleHelper.setLocale(base, language)
}

// SAU (ĐÚNG):
override fun attachBaseContext(base: Context) {
    // ...
    super.attachBaseContext(LocaleHelper.setLocale(base, language))
}
```

---

## 2. LỖI: PasskeyModels.kt - Unresolved Reference

**Lỗi:**
```
Unresolved reference: PasskeyModels
```

**Nguyên nhân:**
- Các data classes không được đặt trong object `PasskeyModels`, nhưng code đang import `PasskeyModels.CreateChallengeRequest`

**Cách sửa:**
```kotlin
// TRƯỚC (SAI):
package com.txahub.app.data.models

data class CreateChallengeRequest(...)
data class CreateChallengeResponse(...)
// ...

// SAU (ĐÚNG):
package com.txahub.app.data.models

object PasskeyModels {
    data class CreateChallengeRequest(...)
    data class CreateChallengeResponse(...)
    // ... tất cả các data classes khác
}
```

---

## 3. LỖI: ChangelogActivity.kt - Missing Layout Files

**Lỗi:**
```
Unresolved reference: activity_changelog
Unresolved reference: item_changelog_version
Unresolved reference: layoutChangelogList, btnClose, tvVersionName, etc.
```

**Nguyên nhân:**
- Thiếu layout XML files: `activity_changelog.xml` và `item_changelog_version.xml`

**Cách sửa:**
- Tạo file `app/src/main/res/layout/activity_changelog.xml` với các view IDs: `layoutChangelogList`, `progressBar`, `btnClose`, `toolbar`
- Tạo file `app/src/main/res/layout/item_changelog_version.xml` với các view IDs: `layoutVersionHeader`, `tvVersionName`, `tvCurrentBadge`, `ivExpand`, `layoutChangelogContent`, `tvReleaseDate`, `webViewChangelog`

**Lưu ý:** Không sử dụng `?attr/colorBackground` vì attribute này không tồn tại trong Material Design. Bỏ `android:background` hoặc dùng màu cụ thể.

---

## 4. LỖI: LoginActivity.kt & SettingsActivity.kt - Type Inference

**Lỗi:**
```
Cannot infer a type for this parameter. Please specify it explicitly.
Suspension functions can be called only within coroutine body
```

**Nguyên nhân:**
- Extension functions `onSuccess` và `onFailure` của `Result` không được type inference đúng trong một số trường hợp

**Cách sửa:**
```kotlin
// TRƯỚC (SAI):
result.onSuccess { authResponse ->
    // ...
}.onFailure { error ->
    // ...
}

// SAU (ĐÚNG):
result.fold(
    onSuccess = { authResponse ->
        // ...
    },
    onFailure = { error ->
        // ...
    }
)
```

**Lưu ý:** Phải có dấu đóng ngoặc `)` sau `onFailure` block.

---

## 5. LỖI: PasskeyManager.kt - Multiple Issues

### 5.1. Unresolved Reference: tasks.await

**Lỗi:**
```
Unresolved reference: tasks
Unresolved reference: await
```

**Cách sửa:**
```kotlin
// TRƯỚC (SAI):
import kotlinx.coroutines.tasks.await
val result = fido2ApiClient?.register(...)?.await()

// SAU (ĐÚNG):
import com.google.android.gms.tasks.Tasks
val task = fido2ApiClient?.getRegisterIntent(...)
val pendingIntent = Tasks.await(task)
```

### 5.2. Type Mismatch: String? vs String

**Lỗi:**
```
Type mismatch: inferred type is String? but String was expected
```

**Cách sửa:**
```kotlin
// TRƯỚC (SAI):
val userEntity = PublicKeyCredentialUserEntity(
    userIdBytes,
    userName,
    userDisplayName,
    userIcon  // String? nhưng cần String
)

// SAU (ĐÚNG):
val userEntity = PublicKeyCredentialUserEntity(
    userIdBytes,
    userName,
    userDisplayName,
    userIcon ?: ""  // Convert String? thành String
)
```

### 5.3. Unresolved Reference: AuthenticatorAttachment, UserVerificationRequirement

**Lỗi:**
```
Unresolved reference: AuthenticatorAttachment
Unresolved reference: UserVerificationRequirement
Unresolved reference: setUserVerificationRequirement
```

**Cách sửa:**
```kotlin
// TRƯỚC (SAI):
.setAttachment(AuthenticatorAttachment.PLATFORM_OR_CROSS_PLATFORM)
.setUserVerificationRequirement(UserVerificationRequirement.REQUIRED)

// SAU (ĐÚNG):
.setAttachment(com.google.android.gms.fido.fido2.api.common.AuthenticatorAttachment.PLATFORM_OR_CROSS_PLATFORM)

// Comment lại setUserVerificationRequirement vì có thể không tồn tại trong một số phiên bản
// authenticatorSelectionBuilder.setUserVerificationRequirement(userVerification)
```

### 5.4. Unresolved Reference: fromIntent, deserializeFromIntent

**Lỗi:**
```
Unresolved reference: fromIntent
Unresolved reference: deserializeFromIntent
```

**Nguyên nhân:**
- Fido2ApiClient trả về `PendingIntent`, cần Activity context để start và nhận result. Không thể lấy response trực tiếp từ PendingIntent trong suspend function.

**Cách sửa tạm thời:**
```kotlin
// Tạm thời return error vì cần Activity context
return Result.failure(Exception("Passkey registration requires Activity context. Please use Activity.startActivityForResult() with the PendingIntent from getRegisterIntent()"))
```

**Lưu ý:** Cần implement lại sau khi có Activity context để xử lý PendingIntent đúng cách.

### 5.5. Unresolved Reference: keyHandle

**Lỗi:**
```
Unresolved reference: keyHandle
```

**Cách sửa:**
```kotlin
// TRƯỚC (SAI):
val keyHandle = result.keyHandle
val id = Base64.encodeToString(keyHandle, ...)

// SAU (ĐÚNG - tạm thời):
json.put("id", "") // TODO: Implement đúng cách lấy keyHandle/keyId
```

### 5.6. Unused Imports

**Lỗi:**
- Không có lỗi compile nhưng nên xóa để code sạch

**Cách sửa:**
```kotlin
// XÓA:
import java.nio.ByteBuffer
import java.util.UUID
```

---

## 6. LỖI: Layout Files - Resource Not Found

**Lỗi:**
```
resource attr/colorBackground (aka com.txahub.app:attr/colorBackground) not found.
```

**Nguyên nhân:**
- `?attr/colorBackground` không phải là Material Design attribute chuẩn

**Cách sửa:**
- Bỏ `android:background="?attr/colorBackground"` hoặc dùng màu cụ thể như `@android:color/white`

---

## 7. LỖI: Syntax - Missing Closing Parenthesis

**Lỗi:**
```
Expecting ')'
```

**Nguyên nhân:**
- Thiếu dấu đóng ngoặc `)` sau `fold()` block

**Cách sửa:**
```kotlin
// TRƯỚC (SAI):
result.fold(
    onSuccess = { ... },
    onFailure = { ... }
}  // Thiếu )

// SAU (ĐÚNG):
result.fold(
    onSuccess = { ... },
    onFailure = { ... }
)  // Có )
```

---

## TÓM TẮT CÁC THAY ĐỔI CHÍNH

1. ✅ Sửa `attachBaseContext` return type
2. ✅ Đặt PasskeyModels data classes vào object
3. ✅ Tạo layout files cho ChangelogActivity
4. ✅ Thay `onSuccess`/`onFailure` bằng `fold()`
5. ✅ Sửa imports và API calls trong PasskeyManager
6. ✅ Bỏ `?attr/colorBackground` trong layouts
7. ✅ Sửa type mismatches
8. ✅ Comment code chưa implement (cần Activity context)

---

## LƯU Ý QUAN TRỌNG

- **PasskeyManager** hiện trả về error vì cần Activity context để xử lý PendingIntent. Cần implement lại sau.
- Các method `convertRegistrationResultToJson()` và `convertAuthenticationResultToJson()` có `@Suppress("UNUSED")` vì chưa được sử dụng.
- Code hiện tại sẽ build được nhưng Passkey features chưa hoạt động đầy đủ.

---

## YÊU CẦU

Vui lòng kiểm tra và sửa tất cả các lỗi trên để đảm bảo project build thành công. Nếu có lỗi mới xuất hiện, hãy mô tả chi tiết lỗi và cung cấp stack trace đầy đủ.

