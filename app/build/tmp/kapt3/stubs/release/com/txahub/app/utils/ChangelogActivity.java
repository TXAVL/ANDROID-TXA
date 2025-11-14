package com.txahub.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0006H\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0015H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/txahub/app/utils/ChangelogActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnClose", "Landroid/widget/Button;", "currentVersion", "", "expandedItems", "", "layoutChangelogList", "Landroid/widget/LinearLayout;", "progressBar", "Landroid/widget/ProgressBar;", "updateChecker", "Lcom/txahub/app/utils/UpdateChecker;", "createChangelogItem", "Landroid/view/View;", "versionChangelog", "Lcom/txahub/app/utils/VersionChangelog;", "getCurrentVersion", "loadAllChangelogs", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupViews", "Companion", "app_release"})
public final class ChangelogActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.txahub.app.utils.UpdateChecker updateChecker;
    private android.widget.LinearLayout layoutChangelogList;
    private android.widget.ProgressBar progressBar;
    private android.widget.Button btnClose;
    @org.jetbrains.annotations.NotNull
    private java.lang.String currentVersion = "";
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> expandedItems = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_VERSION_NAME = "version_name";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_CHANGELOG = "changelog";
    @org.jetbrains.annotations.NotNull
    public static final com.txahub.app.utils.ChangelogActivity.Companion Companion = null;
    
    public ChangelogActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupViews() {
    }
    
    private final void loadAllChangelogs() {
    }
    
    private final android.view.View createChangelogItem(com.txahub.app.utils.VersionChangelog versionChangelog) {
        return null;
    }
    
    private final java.lang.String getCurrentVersion() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/txahub/app/utils/ChangelogActivity$Companion;", "", "()V", "EXTRA_CHANGELOG", "", "EXTRA_VERSION_NAME", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}