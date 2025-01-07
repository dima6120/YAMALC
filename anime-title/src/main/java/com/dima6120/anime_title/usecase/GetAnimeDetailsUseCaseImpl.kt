package com.dima6120.anime_title.usecase

import com.dima6120.core_api.di.DispatcherIO
import com.dima6120.core_api.error.ErrorHandler
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.anime.AnimeDetailsModel
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.network.repository.ApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAnimeDetailsUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val errorHandler: ErrorHandler,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
): GetAnimeDetailsUseCase {

    override suspend fun invoke(animeId: AnimeId): UseCaseResult<AnimeDetailsModel> =
        withContext(ioDispatcher) {
            errorHandler.run {
                apiRepository.getAnimeDetails(animeId)
            }
        }
}