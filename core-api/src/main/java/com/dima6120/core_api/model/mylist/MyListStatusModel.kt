package com.dima6120.core_api.model.mylist

import kotlinx.serialization.Serializable

@Serializable
data class MyListStatusModel(
    val status: ListStatusModel?,
    val score: Int,
    val episodesWatched: Int,
    val startDate: String?,
    val finishDate: String?
)

fun MyListStatusModel?.orNewEntry(): MyListStatusModel =
    this ?: MyListStatusModel(
        status = ListStatusModel.PLAN_TO_WATCH,
        score = 0,
        episodesWatched = 0,
        startDate = null,
        finishDate = null
    )

