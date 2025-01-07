package com.dima6120.core_impl.network.model.anime

import com.dima6120.core_api.model.RatingModel
import com.dima6120.core_api.model.anime.AnimeDetailsModel
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.anime.AnimeSourceModel
import com.dima6120.core_api.model.anime.AnimeStatusModel
import com.dima6120.core_api.model.anime.AnimeTypeModel
import com.dima6120.core_impl.network.model.mylist.MyListStatus
import com.dima6120.core_impl.network.model.Picture
import com.dima6120.core_impl.network.model.Studio
import com.dima6120.core_impl.network.model.mylist.toMyListStatusModel
import com.dima6120.core_impl.network.model.toPictureModel
import com.dima6120.core_impl.network.model.toStudioModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetails(

    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("main_picture")
    val mainPicture: Picture? = null,

    @SerialName("alternative_titles")
    val alternativeTitles: AlternativeTitles? = null,

    @SerialName("start_date")
    val startDate: String? = null,

    @SerialName("end_date")
    val endDate: String? = null,

    @SerialName("synopsis")
    val synopsis: String? = null,

    @SerialName("mean")
    val mean: Float? = null,

    @SerialName("rank")
    val rank: Int? = null,

    @SerialName("popularity")
    val popularity: Int? = null,

    @SerialName("num_list_users")
    val numListUsers: Int,

    @SerialName("num_scoring_users")
    val numScoringUsers: Int,

    @SerialName("genres")
    val genres: List<AnimeGenre>,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("media_type")
    val mediaType: String,

    @SerialName("status")
    val status: String,

    @SerialName("my_list_status")
    val myListStatus: MyListStatus? = null,

    @SerialName("num_episodes")
    val numEpisodes: Int,

    @SerialName("start_season")
    val startSeason: AnimeSeason? = null,

    @SerialName("source")
    val source: String? = null,

    @SerialName("average_episode_duration")
    val averageEpisodeDuration: Int? = null,

    @SerialName("rating")
    val rating: String? = null,

    @SerialName("studios")
    val studios: List<Studio>,

    @SerialName("pictures")
    val pictures: List<Picture>,

    @SerialName("related_anime")
    val relatedAnime: List<RelatedAnime>
)

fun AnimeDetails.toAnimeDetailsModel(): AnimeDetailsModel =
    AnimeDetailsModel(
        id = AnimeId(this.id),
        mainPicture = this.mainPicture?.toPictureModel(),
        englishTitle = this.title,
        japaneseTitle = this.alternativeTitles?.japanese,
        startDate = this.startDate,
        endDate = this.endDate,
        synopsis = this.synopsis,
        score = this.mean,
        rank = this.rank,
        popularity = this.popularity,
        members = this.numListUsers,
        genres = this.genres.map { it.toAnimeGenreModel() },
        type = this.mediaType.toAnimeTypeModel(),
        status = this.status.toAnimeStatusModel(),
        myListStatus = this.myListStatus?.toMyListStatusModel(),
        episodes = this.numEpisodes.takeIf { it != 0 },
        season = this.startSeason?.toAnimeSeasonModel(),
        source = this.source?.toAnimeSourceModel(),
        episodeDuration = this.averageEpisodeDuration,
        rating = this.rating?.toRatingModel(),
        studios = this.studios.map { it.toStudioModel() },
        pictures = this.pictures.map { it.toPictureModel() },
        relatedAnime = this.relatedAnime.map { it.toRelatedAnimeModel() }
    )

fun String.toRatingModel(): RatingModel? =
    when (this) {
        "g" -> RatingModel.G
        "pg" -> RatingModel.PG
        "pg_13" -> RatingModel.PG_13
        "r" -> RatingModel.R
        "r+" -> RatingModel.R_PLUS
        "rx" -> RatingModel.RX
        else -> null
    }

fun String.toAnimeSourceModel(): AnimeSourceModel? =
    when (this) {
        "other" -> AnimeSourceModel.OTHER
        "original" -> AnimeSourceModel.ORIGINAL
        "manga" -> AnimeSourceModel.MANGA
        "4_koma_manga" -> AnimeSourceModel.FOUR_KOMA_MANGA
        "web_manga" -> AnimeSourceModel.WEB_MANGA
        "digital_manga" -> AnimeSourceModel.DIGITAL_MANGA
        "novel" -> AnimeSourceModel.NOVEL
        "light_novel" -> AnimeSourceModel.LIGHT_NOVEL
        "visual_novel" -> AnimeSourceModel.VISUAL_NOVEL
        "game" -> AnimeSourceModel.GAME
        "card_game" -> AnimeSourceModel.CARD_GAME
        "book" -> AnimeSourceModel.BOOK
        "picture_book" -> AnimeSourceModel.PICTURE_BOOK
        "radio" -> AnimeSourceModel.RADIO
        "music" -> AnimeSourceModel.MUSIC
        else -> null
    }

fun String.toAnimeStatusModel(): AnimeStatusModel =
    when (this) {
        "finished_airing" -> AnimeStatusModel.FINISHED_AIRING
        "currently_airing" -> AnimeStatusModel.CURRENTLY_AIRING
        "not_yet_aired" -> AnimeStatusModel.NOT_YET_AIRED
        else -> AnimeStatusModel.NOT_YET_AIRED
    }

fun String.toAnimeTypeModel(): AnimeTypeModel =
    when (this) {
        "tv" -> AnimeTypeModel.TV
        "ova" -> AnimeTypeModel.OVA
        "movie" -> AnimeTypeModel.MOVIE
        "special" -> AnimeTypeModel.SPECIAL
        "ona" -> AnimeTypeModel.ONA
        "music" -> AnimeTypeModel.MUSIC
        "cm" -> AnimeTypeModel.CM
        "unknown" -> AnimeTypeModel.UNKNOWN
        else -> AnimeTypeModel.UNKNOWN
    }
