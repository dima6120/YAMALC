package com.dima6120.core_api.model.anime

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class AnimeId(val id: Int)

fun Int.toAnimeId(): AnimeId = AnimeId(id = this)