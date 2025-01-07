package com.dima6120.core_impl.network.model.anime

import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.anime.RelatedAnimeModel
import com.dima6120.core_api.model.anime.RelationTypeModel
import com.dima6120.core_impl.network.model.toPictureModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelatedAnime(

    @SerialName("relation_type")
    val relationType: String,

    @SerialName("node")
    val node: RelatedAnimeNode
)

fun RelatedAnime.toRelatedAnimeModel(): RelatedAnimeModel =
    RelatedAnimeModel(
        relationType = this.relationType.toRelationTypeModel(),
        id = AnimeId(id = this.node.id),
        title = this.node.title,
        mainPicture = this.node.mainPicture.toPictureModel()
    )

fun String.toRelationTypeModel(): RelationTypeModel =
    when (this) {
        "sequel" -> RelationTypeModel.SEQUEL
        "prequel" -> RelationTypeModel.PREQUEL
        "alternative_setting" -> RelationTypeModel.ALTERNATIVE_SETTING
        "alternative_version" -> RelationTypeModel.ALTERNATIVE_VERSION
        "side_story" -> RelationTypeModel.SIDE_STORY
        "parent_story" -> RelationTypeModel.PARENT_STORY
        "summary" -> RelationTypeModel.SUMMARY
        "full_story" -> RelationTypeModel.FULL_STORY
        "character" -> RelationTypeModel.CHARACTER
        else -> RelationTypeModel.OTHER
    }
