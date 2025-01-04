package com.dima6120.anime_title.usecase

import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.anime.AnimeDetailsModel
import com.dima6120.core_api.model.anime.AnimeId

interface GetAnimeDetailsUseCase {

    suspend operator fun invoke(animeId: AnimeId): UseCaseResult<AnimeDetailsModel>
}