package com.dima6120.core_api.model.profile

data class AnimeStatisticsModel(
    val watchingCount: Int,
    val completedCount: Int,
    val onHoldCount: Int,
    val droppedCount: Int,
    val planToWatchCount: Int,
    val count: Int,
    val days: Float,
    val meanScore: Float
)
