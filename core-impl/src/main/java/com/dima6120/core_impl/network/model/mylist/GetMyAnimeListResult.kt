package com.dima6120.core_impl.network.model.mylist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMyAnimeListResult(

    @SerialName("data")
    val data: List<MyListAnime>
)
