package com.dima6120.core_api.model.anime

import com.dima6120.core_api.model.PictureModel

data class RelatedAnimeModel(
    val relationType: RelationTypeModel,
    val id: AnimeId,
    val title: String,
    val mainPicture: PictureModel
)
