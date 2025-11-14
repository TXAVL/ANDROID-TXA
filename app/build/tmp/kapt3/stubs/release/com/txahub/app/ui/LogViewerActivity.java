package com.txahub.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0002\u001c\u001dB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0006H\u0002J\b\u0010\u0011\u001a\u00020\fH\u0002J\u0012\u0010\u0012\u001a\u00020\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\b\u0010\u0015\u001a\u00020\fH\u0014J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\fH\u0002J\u0010\u0010\u0019\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u001a\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u001b\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/txahub/app/ui/LogViewerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/txahub/app/databinding/ActivityLogViewerBinding;", "currentFilter", "Lcom/txahub/app/ui/LogViewerActivity$LogType;", "logAdapter", "Lcom/txahub/app/ui/LogViewerActivity$LogFileAdapter;", "logWriter", "Lcom/txahub/app/utils/LogWriter;", "deleteLogFile", "", "logFile", "Ljava/io/File;", "filterLogs", "logType", "loadLogs", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "onSupportNavigateUp", "", "setupUI", "shareLogFile", "showLogFileDialog", "updateTabSelection", "LogFileAdapter", "LogType", "app_release"})
public final class LogViewerActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.txahub.app.databinding.ActivityLogViewerBinding binding;
    private com.txahub.app.utils.LogWriter logWriter;
    private com.txahub.app.ui.LogViewerActivity.LogFileAdapter logAdapter;
    @org.jetbrains.annotations.NotNull
    private com.txahub.app.ui.LogViewerActivity.LogType currentFilter = com.txahub.app.ui.LogViewerActivity.LogType.ALL;
    
    public LogViewerActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupUI() {
    }
    
    private final void filterLogs(com.txahub.app.ui.LogViewerActivity.LogType logType) {
    }
    
    private final void updateTabSelection() {
    }
    
    private final void loadLogs() {
    }
    
    private final void showLogFileDialog(java.io.File logFile) {
    }
    
    private final void shareLogFile(java.io.File logFile) {
    }
    
    private final void deleteLogFile(java.io.File logFile) {
    }
    
    @java.lang.Override
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    @java.lang.Override
    protected void onResume() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0016B\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\u0002\u0010\u0007J\b\u0010\n\u001a\u00020\u000bH\u0016J\u001c\u0010\f\u001a\u00020\u00062\n\u0010\r\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000bH\u0016J\u001c\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000bH\u0016J\u0014\u0010\u0013\u001a\u00020\u00062\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/txahub/app/ui/LogViewerActivity$LogFileAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/txahub/app/ui/LogViewerActivity$LogFileAdapter$LogFileViewHolder;", "onItemClick", "Lkotlin/Function1;", "Ljava/io/File;", "", "(Lkotlin/jvm/functions/Function1;)V", "logFiles", "", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "submitList", "files", "", "LogFileViewHolder", "app_release"})
    static final class LogFileAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.txahub.app.ui.LogViewerActivity.LogFileAdapter.LogFileViewHolder> {
        @org.jetbrains.annotations.NotNull
        private final kotlin.jvm.functions.Function1<java.io.File, kotlin.Unit> onItemClick = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.io.File> logFiles = null;
        
        public LogFileAdapter(@org.jetbrains.annotations.NotNull
        kotlin.jvm.functions.Function1<? super java.io.File, kotlin.Unit> onItemClick) {
            super();
        }
        
        public final void submitList(@org.jetbrains.annotations.NotNull
        java.util.List<? extends java.io.File> files) {
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.txahub.app.ui.LogViewerActivity.LogFileAdapter.LogFileViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull
        com.txahub.app.ui.LogViewerActivity.LogFileAdapter.LogFileViewHolder holder, int position) {
        }
        
        @java.lang.Override
        public int getItemCount() {
            return 0;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/txahub/app/ui/LogViewerActivity$LogFileAdapter$LogFileViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/txahub/app/ui/LogViewerActivity$LogFileAdapter;Landroid/view/View;)V", "tvFileDate", "Landroid/widget/TextView;", "kotlin.jvm.PlatformType", "tvFileName", "tvFileSize", "bind", "", "logFile", "Ljava/io/File;", "app_release"})
        public final class LogFileViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            private final android.widget.TextView tvFileName = null;
            private final android.widget.TextView tvFileSize = null;
            private final android.widget.TextView tvFileDate = null;
            
            public LogFileViewHolder(@org.jetbrains.annotations.NotNull
            android.view.View itemView) {
                super(null);
            }
            
            public final void bind(@org.jetbrains.annotations.NotNull
            java.io.File logFile) {
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\r"}, d2 = {"Lcom/txahub/app/ui/LogViewerActivity$LogType;", "", "prefix", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getPrefix", "()Ljava/lang/String;", "ALL", "API", "APP", "CRASH", "UPDATE", "PASSKEY", "app_release"})
    public static enum LogType {
        /*public static final*/ ALL /* = new ALL(null) */,
        /*public static final*/ API /* = new API(null) */,
        /*public static final*/ APP /* = new APP(null) */,
        /*public static final*/ CRASH /* = new CRASH(null) */,
        /*public static final*/ UPDATE /* = new UPDATE(null) */,
        /*public static final*/ PASSKEY /* = new PASSKEY(null) */;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String prefix = null;
        
        LogType(java.lang.String prefix) {
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getPrefix() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.txahub.app.ui.LogViewerActivity.LogType> getEntries() {
            return null;
        }
    }
}