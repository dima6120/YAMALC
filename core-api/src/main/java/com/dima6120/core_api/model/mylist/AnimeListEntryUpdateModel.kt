package com.dima6120.core_api.model.mylist

import com.dima6120.core_api.model.anime.AnimeId

data class AnimeListEntryUpdateModel(
    val animeId: AnimeId,
    val status: ListStatusModel = ListStatusModel.PLAN_TO_WATCH,
    val score: Int = 0,
    val episodesWatched: Int = 0,
)