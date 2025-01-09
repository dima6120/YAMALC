package com.dima6120.core_impl.network.model.anime

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAnimeListResult(
    @SerialName("data")
    val data: List<GetAnimeListEntry>
)


@Serializable
data class GetAnimeListEntry(
    @SerialName("node")
    val animeBriefDetails: AnimeBriefDetails
)