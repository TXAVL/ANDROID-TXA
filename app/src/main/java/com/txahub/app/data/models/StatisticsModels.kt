package com.txahub.app.data.models

import com.google.gson.annotations.SerializedName

data class UserStatistics(
    @SerializedName("total_clicks")
    val totalClicks: Int = 0,
    @SerializedName("total_links")
    val totalLinks: Int = 0,
    @SerializedName("total_projects")
    val totalProjects: Int = 0,
    @SerializedName("clicks_today")
    val clicksToday: Int = 0,
    @SerializedName("clicks_this_week")
    val clicksThisWeek: Int = 0,
    @SerializedName("clicks_this_month")
    val clicksThisMonth: Int = 0,
    @SerializedName("top_links")
    val topLinks: List<TopLink> = emptyList()
)

data class TopLink(
    val id: Int,
    val url: String,
    @SerializedName("location_url")
    val locationUrl: String,
    val clicks: Int
)


