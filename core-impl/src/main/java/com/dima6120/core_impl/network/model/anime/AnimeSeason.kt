package com.dima6120.core_impl.network.model.anime

import com.dima6120.core_api.model.SeasonModel
import com.dima6120.core_api.model.anime.AnimeSeasonModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeSeason(

    @SerialName("year")
    val year: Int,

    @SerialName("season")
    val season: String
)

fun AnimeSeason.toAnimeSeasonModel(): AnimeSeasonModel =
    AnimeSeasonModel(
        year = this.year,
        season = this.season.toSeasonModel()
    )

fun String.toSeasonModel(): SeasonModel? =
    when (this) {
        "winter" -> SeasonModel.WINTER
        "spring" -> SeasonModel.SPRING
        "summer" -> SeasonModel.SUMMER
        "fall" -> SeasonModel.FALL
        else -> null
    }
