package com.dima6120.core_api.model.anime

import com.dima6120.core_api.model.PictureModel
import com.dima6120.core_api.model.RatingModel
import com.dima6120.core_api.model.StudioModel
import com.dima6120.core_api.model.mylist.MyListStatusModel

data class AnimeDetailsModel(
    val id: AnimeId,
    val mainPicture: PictureModel?,
    val englishTitle: String, // title
    val japaneseTitle: String?, // alternativeTitles.japanese
    val startDate: String?,
    val endDate: String?,
    val synopsis: String?,
    val score: Float?, // mean
    val rank: Int?,
    val popularity: Int?,
    val members: Int, // numListUsers
    val genres: List<AnimeGenreModel>,
    val type: AnimeTypeModel, // mediaType
    val status: AnimeStatusModel,
    val myListStatus: MyListStatusModel?,
    val episodes: Int?,
    val season: AnimeSeasonModel?, // startSeason
    val source: AnimeSourceModel?,
    val episodeDuration: Int?, // averageEpisodeDuration
    val rating: RatingModel?,
    val studios: List<StudioModel>,
    val pictures: List<PictureModel>,
    val relatedAnime: List<RelatedAnimeModel>
)

fun AnimeDetailsModel.toAnimeBriefDetailsModel(): AnimeBriefDetailsModel =
    AnimeBriefDetailsModel(
        id = this.id,
        title = this.englishTitle,
        mainPicture = this.mainPicture,
        type = this.type,
        season = this.season,
        status = this.status,
        episodes = this.episodes,
        members = this.members,
        score = this.score,
        myListStatus = this.myListStatus
    )