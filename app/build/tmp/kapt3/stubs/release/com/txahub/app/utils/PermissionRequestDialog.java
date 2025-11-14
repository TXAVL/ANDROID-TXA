package com.txahub.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\u0018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J)\u0010\u000f\u001a\u00020\f2!\u0010\u0010\u001a\u001d\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\f0\u0011J\u0018\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/txahub/app/utils/PermissionRequestDialog;", "", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "permissionManager", "Lcom/txahub/app/utils/PermissionManager;", "createPermissionItemView", "Landroid/view/View;", "item", "Lcom/txahub/app/utils/PermissionRequestDialog$PermissionItem;", "requestPermission", "", "permissionInfo", "Lcom/txahub/app/utils/PermissionInfo;", "show", "callback", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "allGranted", "updatePermissionItemView", "itemView", "PermissionItem", "app_release"})
public final class PermissionRequestDialog {
    @org.jetbrains.annotations.NotNull
    private final android.app.Activity activity = null;
    @org.jetbrains.annotations.NotNull
    private final com.txahub.app.utils.PermissionManager permissionManager = null;
    
    public PermissionRequestDialog(@org.jetbrains.annotations.NotNull
    android.app.Activity activity) {
        super();
    }
    
    /**
     * Hiển thị dialog yêu cầu quyền
     */
    public final void show(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> callback) {
    }
    
    /**
     * Tạo view cho một item quyền
     */
    private final android.view.View createPermissionItemView(com.txahub.app.utils.PermissionRequestDialog.PermissionItem item) {
        return null;
    }
    
    /**
     * Cập nhật view của item quyền
     */
    private final void updatePermissionItemView(android.view.View itemView, com.txahub.app.utils.PermissionRequestDialog.PermissionItem item) {
    }
    
    /**
     * Yêu cầu quyền
     */
    private final void requestPermission(com.txahub.app.utils.PermissionInfo permissionInfo) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000b\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0013"}, d2 = {"Lcom/txahub/app/utils/PermissionRequestDialog$PermissionItem;", "", "permission", "Lcom/txahub/app/utils/PermissionInfo;", "isGranted", "", "(Lcom/txahub/app/utils/PermissionInfo;Z)V", "()Z", "getPermission", "()Lcom/txahub/app/utils/PermissionInfo;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "", "app_release"})
    static final class PermissionItem {
        @org.jetbrains.annotations.NotNull
        private final com.txahub.app.utils.PermissionInfo permission = null;
        private final boolean isGranted = false;
        
        public PermissionItem(@org.jetbrains.annotations.NotNull
        com.txahub.app.utils.PermissionInfo permission, boolean isGranted) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.txahub.app.utils.PermissionInfo getPermission() {
            return null;
        }
        
        public final boolean isGranted() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.txahub.app.utils.PermissionInfo component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.txahub.app.utils.PermissionRequestDialog.PermissionItem copy(@org.jetbrains.annotations.NotNull
        com.txahub.app.utils.PermissionInfo permission, boolean isGranted) {
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
}