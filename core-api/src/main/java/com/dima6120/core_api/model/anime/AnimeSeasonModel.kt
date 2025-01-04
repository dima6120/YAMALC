package com.dima6120.core_api.model.anime

import com.dima6120.core_api.model.SeasonModel

data class AnimeSeasonModel(
    val year: Int,
    val season: SeasonModel?
)
