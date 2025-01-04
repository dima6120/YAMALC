package com.dima6120.core_impl.network.service

import com.dima6120.core_impl.network.model.anime.AnimeDetails
import com.dima6120.core_impl.network.model.user.Profile
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users/@me?fields=anime_statistics")
    suspend fun getProfile(): Profile


    @GET("anime/{anime_id}")
    suspend fun getAnimeDetails(
        @Path("anime_id") animeId: Int,
        @Query("fields") fields: String
    ): AnimeDetails
}