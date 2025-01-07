package com.dima6120.core_impl.network.model.user

import com.dima6120.core_api.model.profile.AnimeStatisticsModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeStatistics(

    @SerialName("num_items_watching")
    val watchingCount: Int,

    @SerialName("num_items_completed")
    val completedCount: Int,

    @SerialName("num_items_on_hold")
    val onHoldCount: Int,

    @SerialName("num_items_dropped")
    val droppedCount: Int,

    @SerialName("num_items_plan_to_watch")
    val planToWatchCount: Int,

    @SerialName("num_items")
    val count: Int,

    @SerialName("num_days")
    val days: Float,

    @SerialName("mean_score")
    val meanScore: Float
)

fun AnimeStatistics.toAnimeStatisticsModel(): AnimeStatisticsModel =
    AnimeStatisticsModel(
        watchingCount = this.watchingCount,
        completedCount = this.completedCount,
        onHoldCount = this.onHoldCount,
        droppedCount = this.droppedCount,
        planToWatchCount = this.planToWatchCount,
        count = this.count,
        days = this.days,
        meanScore = this.meanScore
    )
