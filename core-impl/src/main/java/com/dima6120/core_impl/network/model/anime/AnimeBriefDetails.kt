package com.dima6120.core_impl.network.model.anime

import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel
import com.dima6120.core_api.model.anime.toAnimeId
import com.dima6120.core_impl.network.model.Picture
import com.dima6120.core_impl.network.model.mylist.MyListStatus
import com.dima6120.core_impl.network.model.mylist.toMyListStatusModel
import com.dima6120.core_impl.network.model.toPictureModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeBriefDetails(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("main_picture")
    val mainPicture: Picture? = null,

    @SerialName("media_type")
    val mediaType: String,

    @SerialName("start_season")
    val startSeason: AnimeSeason? = null,

    @SerialName("status")
    val status: String,

    @SerialName("num_list_users")
    val numListUsers: Int,

    @SerialName("mean")
    val mean: Float? = null,

    @SerialName("num_episodes")
    val numEpisodes: Int,

    @SerialName("my_list_status")
    val myListStatus: MyListStatus? = null,
)

fun AnimeBriefDetails.toAnimeBriefDetailsModel(): AnimeBriefDetailsModel =
    AnimeBriefDetailsModel(
        id = this.id.toAnimeId(),
        title = this.title,
        mainPicture = this.mainPicture?.toPictureModel(),
        type = this.mediaType.toAnimeTypeModel(),
        season = this.startSeason?.toAnimeSeasonModel(),
        status = this.status.toAnimeStatusModel(),
        members = this.numListUsers,
        score = this.mean,
        episodes = this.numEpisodes.takeIf { it != 0 },
        myListStatus = this.myListStatus?.toMyListStatusModel()
    )
