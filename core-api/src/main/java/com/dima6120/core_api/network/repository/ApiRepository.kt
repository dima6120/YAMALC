package com.dima6120.core_api.network.repository

import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel
import com.dima6120.core_api.model.anime.AnimeDetailsModel
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.mylist.AnimeListEntryUpdateModel
import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.core_api.model.mylist.MyListAnimeModel
import com.dima6120.core_api.model.profile.ProfileModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ApiRepository {

    fun getProfileFlow(): StateFlow<ProfileModel?>

    fun getUpdatedAnimeListEntryFlow(): SharedFlow<AnimeListEntryUpdateModel>

    fun getDeletedAnimeListEntryFlow(): SharedFlow<AnimeId>

    suspend fun getProfile(force: Boolean = false): ProfileModel

    suspend fun getAnimeDetails(animeId: AnimeId): AnimeDetailsModel

    suspend fun getAnimeList(query: String, page: Int = 0): List<AnimeBriefDetailsModel>

    suspend fun getMyAnimeList(status: ListStatusModel, page: Int = 0): List<MyListAnimeModel>

    suspend fun updateAnimeListEntry(animeListEntryUpdateModel: AnimeListEntryUpdateModel)

    suspend fun deleteAnimeListEntry(animeId: AnimeId)
}