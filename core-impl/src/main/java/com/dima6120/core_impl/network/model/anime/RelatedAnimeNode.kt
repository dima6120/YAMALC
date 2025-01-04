package com.dima6120.core_impl.network.model.anime

import com.dima6120.core_impl.network.model.Picture
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelatedAnimeNode(

    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("main_picture")
    val mainPicture: Picture
)
