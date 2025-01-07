package com.dima6120.core_api.model.anime

import com.dima6120.core_api.model.PictureModel
import com.dima6120.core_api.model.mylist.MyListStatusModel

data class AnimeBriefDetailsModel(
    val id: AnimeId,
    val title: String,
    val mainPicture: PictureModel?,
    val type: AnimeTypeModel, // mediaType
    val season: AnimeSeasonModel?, // startSeason
    val status: AnimeStatusModel,
    val episodes: Int?,
    val members: Int, // numListUsers
    val score: Float?, // mean
    val myListStatus: MyListStatusModel?,
)
