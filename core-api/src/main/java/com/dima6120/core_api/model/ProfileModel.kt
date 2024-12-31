package com.dima6120.core_api.model

data class ProfileModel(
    val id: Int,
    val name: String,
    val picture: String,
    val gender: GenderModel,
    val birthday: String?,
    val location: String?,
    val joinedAt: String,
    val animeStatistics: AnimeStatisticsModel
)
