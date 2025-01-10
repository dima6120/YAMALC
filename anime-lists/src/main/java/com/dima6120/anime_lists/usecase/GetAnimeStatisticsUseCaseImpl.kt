package com.dima6120.anime_lists.usecase

import com.dima6120.core_api.di.DispatcherIO
import com.dima6120.core_api.error.ErrorHandler
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.profile.AnimeStatisticsModel
import com.dima6120.core_api.network.repository.ApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAnimeStatisticsUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val errorHandler: ErrorHandler,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
): GetAnimeStatisticsUseCase {

    override suspend fun invoke(): UseCaseResult<AnimeStatisticsModel> =
        withContext(ioDispatcher) {
            errorHandler.run {
                apiRepository.getProfile().animeStatistics
            }
        }
}