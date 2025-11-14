package com.txahub.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0010J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003JR\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010!J\u0013\u0010\"\u001a\u00020\u00032\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010$\u001a\u00020\bH\u00d6\u0001J\t\u0010%\u001a\u00020\u0005H\u00d6\u0001R\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u001a\u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006&"}, d2 = {"Lcom/txahub/app/data/models/VerifyRegistrationResponse;", "", "success", "", "message", "", "deviceLocation", "backupState", "", "oldPasskeyDeleted", "backup", "Lcom/txahub/app/data/models/BackupInfo;", "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Boolean;Lcom/txahub/app/data/models/BackupInfo;)V", "getBackup", "()Lcom/txahub/app/data/models/BackupInfo;", "getBackupState", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getDeviceLocation", "()Ljava/lang/String;", "getMessage", "getOldPasskeyDeleted", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getSuccess", "()Z", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Boolean;Lcom/txahub/app/data/models/BackupInfo;)Lcom/txahub/app/data/models/VerifyRegistrationResponse;", "equals", "other", "hashCode", "toString", "app_release"})
public final class VerifyRegistrationResponse {
    @com.google.gson.annotations.SerializedName(value = "success")
    private final boolean success = false;
    @com.google.gson.annotations.SerializedName(value = "message")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String message = null;
    @com.google.gson.annotations.SerializedName(value = "device_location")
    @org.jetbrains.annotations.Nullable
    private final java.lang.String deviceLocation = null;
    @com.google.gson.annotations.SerializedName(value = "backup_state")
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer backupState = null;
    @com.google.gson.annotations.SerializedName(value = "old_passkey_deleted")
    @org.jetbrains.annotations.Nullable
    private final java.lang.Boolean oldPasskeyDeleted = null;
    @com.google.gson.annotations.SerializedName(value = "backup")
    @org.jetbrains.annotations.Nullable
    private final com.txahub.app.data.models.BackupInfo backup = null;
    
    public VerifyRegistrationResponse(boolean success, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.Nullable
    java.lang.String deviceLocation, @org.jetbrains.annotations.Nullable
    java.lang.Integer backupState, @org.jetbrains.annotations.Nullable
    java.lang.Boolean oldPasskeyDeleted, @org.jetbrains.annotations.Nullable
    com.txahub.app.data.models.BackupInfo backup) {
        super();
    }
    
    public final boolean getSuccess() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDeviceLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getBackupState() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Boolean getOldPasskeyDeleted() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.txahub.app.data.models.BackupInfo getBackup() {
        return null;
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Boolean component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.txahub.app.data.models.BackupInfo component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.txahub.app.data.models.VerifyRegistrationResponse copy(boolean success, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.Nullable
    java.lang.String deviceLocation, @org.jetbrains.annotations.Nullable
    java.lang.Integer backupState, @org.jetbrains.annotations.Nullable
    java.lang.Boolean oldPasskeyDeleted, @org.jetbrains.annotations.Nullable
    com.txahub.app.data.models.BackupInfo backup) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}