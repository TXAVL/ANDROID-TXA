package com.txahub.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u000fJH\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001aJ\u0013\u0010\u001b\u001a\u00020\u00032\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0005H\u00d6\u0001R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u001a\u0010\b\u001a\u0004\u0018\u00010\t8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006 "}, d2 = {"Lcom/txahub/app/data/models/BackupInfo;", "", "success", "", "fileUrl", "", "fileId", "fileName", "fileSize", "", "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;)V", "getFileId", "()Ljava/lang/String;", "getFileName", "getFileSize", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getFileUrl", "getSuccess", "()Z", "component1", "component2", "component3", "component4", "component5", "copy", "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;)Lcom/txahub/app/data/models/BackupInfo;", "equals", "other", "hashCode", "", "toString", "app_release"})
public final class BackupInfo {
    @com.google.gson.annotations.SerializedName(value = "success")
    private final boolean success = false;
    @com.google.gson.annotations.SerializedName(value = "file_url")
    @org.jetbrains.annotations.Nullable
    private final java.lang.String fileUrl = null;
    @com.google.gson.annotations.SerializedName(value = "file_id")
    @org.jetbrains.annotations.Nullable
    private final java.lang.String fileId = null;
    @com.google.gson.annotations.SerializedName(value = "file_name")
    @org.jetbrains.annotations.Nullable
    private final java.lang.String fileName = null;
    @com.google.gson.annotations.SerializedName(value = "file_size")
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long fileSize = null;
    
    public BackupInfo(boolean success, @org.jetbrains.annotations.Nullable
    java.lang.String fileUrl, @org.jetbrains.annotations.Nullable
    java.lang.String fileId, @org.jetbrains.annotations.Nullable
    java.lang.String fileName, @org.jetbrains.annotations.Nullable
    java.lang.Long fileSize) {
        super();
    }
    
    public final boolean getSuccess() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getFileUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getFileId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getFileName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getFileSize() {
        return null;
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.txahub.app.data.models.BackupInfo copy(boolean success, @org.jetbrains.annotations.Nullable
    java.lang.String fileUrl, @org.jetbrains.annotations.Nullable
    java.lang.String fileId, @org.jetbrains.annotations.Nullable
    java.lang.String fileName, @org.jetbrains.annotations.Nullable
    java.lang.Long fileSize) {
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