package com.dima6120.anime_title_api

import androidx.navigation.NavType
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.navigation.CustomNavTypes
import com.dima6120.core_api.navigation.Route
import kotlinx.serialization.Serializable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Serializable
data class AnimeTitleRoute(val animeId: AnimeId): Route