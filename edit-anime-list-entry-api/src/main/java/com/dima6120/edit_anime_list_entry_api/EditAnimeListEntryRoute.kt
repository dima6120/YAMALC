package com.dima6120.edit_anime_list_entry_api

import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.mylist.MyListStatusModel
import com.dima6120.core_api.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class EditAnimeListEntryRoute(
    val animeBriefDetailsModel: AnimeBriefDetailsModel,
    val myListStatusModel: MyListStatusModel
): Route
