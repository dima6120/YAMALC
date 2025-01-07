package com.dima6120.core_api.model.mylist

data class MyListStatusModel(
    val status: ListStatusModel?,
    val score: Int,
    val episodesWatched: Int,
    val startDate: String?,
    val finishDate: String?
)
