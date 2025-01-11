package com.dima6120.core_impl.network.service

import com.dima6120.core_impl.network.model.anime.AnimeDetails
import com.dima6120.core_impl.network.model.anime.GetAnimeListResult
import com.dima6120.core_impl.network.model.mylist.GetMyAnimeListResult
import com.dima6120.core_impl.network.model.user.Profile
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
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

    @GET("anime")
    suspend fun getAnimeList(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("fields") fields: String
    ): GetAnimeListResult

    @GET("users/{user}/animelist")
    suspend fun getUserAnimeList(
        @Path("user") user: String,
        @Query("status") status: String,
        @Query("sort") sort: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("fields") fields: String
    ): GetMyAnimeListResult

    @FormUrlEncoded
    @PATCH("anime/{anime_id}/my_list_status")
    suspend fun updateAnimeListEntry(
        @Path("anime_id") animeId: Int,
        @Field("status") status: String? = null,
        @Field("score") score: Int? = null,
        @Field("num_watched_episodes") watchedEpisodes: Int? = null
    )

    @DELETE("anime/{anime_id}/my_list_status")
    suspend fun deleteAnimeListEntry(
        @Path("anime_id") animeId: Int
    )
}