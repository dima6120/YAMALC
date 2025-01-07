package com.dima6120.core_impl.network.model.anime

import com.dima6120.core_api.model.anime.AnimeGenreModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeGenre(

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)

fun AnimeGenre.toAnimeGenreModel(): AnimeGenreModel =
    AnimeGenreModel(
        id = this.id,
        name = this.name
    )
