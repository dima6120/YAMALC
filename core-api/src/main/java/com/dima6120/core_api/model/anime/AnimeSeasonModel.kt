package com.dima6120.core_api.model.anime

import com.dima6120.core_api.model.SeasonModel
import kotlinx.serialization.Serializable

@Serializable
data class AnimeSeasonModel(
    val year: Int,
    val season: SeasonModel?
)
