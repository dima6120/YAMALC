package com.dima6120.core_api.network.repository

import com.dima6120.core_api.model.anime.AnimeDetailsModel
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.profile.ProfileModel

interface ApiRepository {

    suspend fun getProfile(): ProfileModel

    suspend fun getAnimeDetails(animeId: AnimeId): AnimeDetailsModel
}