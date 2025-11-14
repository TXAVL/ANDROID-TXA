package com.txahub.app.data.models

import com.google.gson.annotations.SerializedName

/**
 * Models cho Passkey API
 */
object PasskeyModels {
    data class CreateChallengeRequest(
    @SerializedName("type")
    val type: String // "registration" hoặc "authentication"
)

data class CreateChallengeResponse(
    @SerializedName("challenge_id")
    val challengeId: String,
    @SerializedName("challenge")
    val challenge: String,
    @SerializedName("rp")
    val rp: RelyingParty,
    @SerializedName("pubKeyCredParams")
    val pubKeyCredParams: List<PubKeyCredParam>,
    @SerializedName("timeout")
    val timeout: Long,
    @SerializedName("attestation")
    val attestation: String,
    @SerializedName("userVerification")
    val userVerification: String,
    @SerializedName("authenticatorSelection")
    val authenticatorSelection: AuthenticatorSelection,
    @SerializedName("user")
    val user: UserInfo? = null // Chỉ có khi type = "registration"
)

data class RelyingParty(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("icon")
    val icon: String? = null
)

data class PubKeyCredParam(
    @SerializedName("type")
    val type: String,
    @SerializedName("alg")
    val alg: Int
)

data class AuthenticatorSelection(
    @SerializedName("residentKey")
    val residentKey: String,
    @SerializedName("userVerification")
    val userVerification: String
)

data class UserInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("displayName")
    val displayName: String,
    @SerializedName("icon")
    val icon: String? = null
)

data class VerifyRegistrationRequest(
    @SerializedName("challenge_id")
    val challengeId: String,
    @SerializedName("credential")
    val credential: String, // JSON string
    @SerializedName("name")
    val name: String? = null
)

data class VerifyRegistrationResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("device_location")
    val deviceLocation: String? = null,
    @SerializedName("backup_state")
    val backupState: Int? = null,
    @SerializedName("old_passkey_deleted")
    val oldPasskeyDeleted: Boolean? = null,
    @SerializedName("backup")
    val backup: BackupInfo? = null
)

data class BackupInfo(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("file_url")
    val fileUrl: String? = null,
    @SerializedName("file_id")
    val fileId: String? = null,
    @SerializedName("file_name")
    val fileName: String? = null,
    @SerializedName("file_size")
    val fileSize: Long? = null
)

data class VerifyAuthenticationRequest(
    @SerializedName("challenge_id")
    val challengeId: String,
    @SerializedName("credential")
    val credential: String // JSON string
)

data class VerifyAuthenticationResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: com.txahub.app.data.models.AuthResponse? = null // Chỉ có khi đăng nhập thành công
)
}

