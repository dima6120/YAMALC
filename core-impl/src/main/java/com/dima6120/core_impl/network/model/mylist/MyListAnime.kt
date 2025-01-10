package com.dima6120.core_impl.network.model.mylist

import com.dima6120.core_api.model.mylist.MyListAnimeModel
import com.dima6120.core_impl.network.model.anime.AnimeBriefDetails
import com.dima6120.core_impl.network.model.anime.toAnimeBriefDetailsModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyListAnime(

    @SerialName("node")
    val anime: AnimeBriefDetails,
)

fun MyListAnime.toMyAnimeListModel(): MyListAnimeModel =
    MyListAnimeModel(
        anime = this.anime.toAnimeBriefDetailsModel(),
        status = this.anime.myListStatus!!.toMyListStatusModel()
    )
