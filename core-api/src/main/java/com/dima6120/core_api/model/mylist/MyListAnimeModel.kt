package com.dima6120.core_api.model.mylist

import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel

data class MyListAnimeModel(
    val anime: AnimeBriefDetailsModel,
    val status: MyListStatusModel
)
