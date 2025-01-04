package com.dima6120.core_impl.network.model.mylist

import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.core_api.model.mylist.MyListStatusModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyListStatus(

    @SerialName("status")
    val status: String? = null,

    @SerialName("score")
    val score: Int,

    @SerialName("num_episodes_watched")
    val numEpisodesWatched: Int,

    @SerialName("start_date")
    val startDate: String? = null,

    @SerialName("finish_date")
    val finishDate: String? = null
)

fun MyListStatus.toMyListStatusModel(): MyListStatusModel =
    MyListStatusModel(
        status = this.status?.toListStatusModel(),
        score = this.score,
        episodesWatched = this.numEpisodesWatched,
        startDate = this.startDate,
        finishDate = this.finishDate
    )

fun String.toListStatusModel(): ListStatusModel? =
    when (this) {
        "watching" -> ListStatusModel.WATCHING
        "completed" -> ListStatusModel.COMPLETED
        "on_hold" -> ListStatusModel.ON_HOLD
        "dropped" -> ListStatusModel.DROPPED
        "plan_to_watch" -> ListStatusModel.PLAN_TO_WATCH
        else -> null
    }
