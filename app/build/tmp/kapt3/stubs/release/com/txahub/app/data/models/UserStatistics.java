package com.txahub.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BQ\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003JU\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\"\u001a\u00020#H\u00d6\u0001R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000e\u00a8\u0006$"}, d2 = {"Lcom/txahub/app/data/models/UserStatistics;", "", "totalClicks", "", "totalLinks", "totalProjects", "clicksToday", "clicksThisWeek", "clicksThisMonth", "topLinks", "", "Lcom/txahub/app/data/models/TopLink;", "(IIIIIILjava/util/List;)V", "getClicksThisMonth", "()I", "getClicksThisWeek", "getClicksToday", "getTopLinks", "()Ljava/util/List;", "getTotalClicks", "getTotalLinks", "getTotalProjects", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "", "app_release"})
public final class UserStatistics {
    @com.google.gson.annotations.SerializedName(value = "total_clicks")
    private final int totalClicks = 0;
    @com.google.gson.annotations.SerializedName(value = "total_links")
    private final int totalLinks = 0;
    @com.google.gson.annotations.SerializedName(value = "total_projects")
    private final int totalProjects = 0;
    @com.google.gson.annotations.SerializedName(value = "clicks_today")
    private final int clicksToday = 0;
    @com.google.gson.annotations.SerializedName(value = "clicks_this_week")
    private final int clicksThisWeek = 0;
    @com.google.gson.annotations.SerializedName(value = "clicks_this_month")
    private final int clicksThisMonth = 0;
    @com.google.gson.annotations.SerializedName(value = "top_links")
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.txahub.app.data.models.TopLink> topLinks = null;
    
    public UserStatistics(int totalClicks, int totalLinks, int totalProjects, int clicksToday, int clicksThisWeek, int clicksThisMonth, @org.jetbrains.annotations.NotNull
    java.util.List<com.txahub.app.data.models.TopLink> topLinks) {
        super();
    }
    
    public final int getTotalClicks() {
        return 0;
    }
    
    public final int getTotalLinks() {
        return 0;
    }
    
    public final int getTotalProjects() {
        return 0;
    }
    
    public final int getClicksToday() {
        return 0;
    }
    
    public final int getClicksThisWeek() {
        return 0;
    }
    
    public final int getClicksThisMonth() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.txahub.app.data.models.TopLink> getTopLinks() {
        return null;
    }
    
    public UserStatistics() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.txahub.app.data.models.TopLink> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.txahub.app.data.models.UserStatistics copy(int totalClicks, int totalLinks, int totalProjects, int clicksToday, int clicksThisWeek, int clicksThisMonth, @org.jetbrains.annotations.NotNull
    java.util.List<com.txahub.app.data.models.TopLink> topLinks) {
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