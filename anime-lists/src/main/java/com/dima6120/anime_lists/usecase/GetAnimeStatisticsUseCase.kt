package com.dima6120.anime_lists.usecase

import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.profile.AnimeStatisticsModel

interface GetAnimeStatisticsUseCase {

    suspend operator fun invoke(): UseCaseResult<AnimeStatisticsModel>
}