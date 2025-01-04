package com.dima6120.core_impl.network.service

import com.dima6120.core_impl.network.model.user.Profile
import retrofit2.http.GET

interface ApiService {

    @GET("users/@me?fields=anime_statistics")
    suspend fun getProfile(): Profile
}