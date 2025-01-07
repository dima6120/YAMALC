package com.dima6120.search.usecase

import com.dima6120.core_api.di.DispatcherIO
import com.dima6120.core_api.error.ErrorHandler
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel
import com.dima6120.core_api.network.repository.ApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAnimeListUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val errorHandler: ErrorHandler,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
): GetAnimeListUseCase {

    override suspend fun invoke(query: String, page: Int): UseCaseResult<List<AnimeBriefDetailsModel>> =
        withContext(ioDispatcher) {
            errorHandler.run {
                apiRepository.getAnimeList(query, page)
            }
        }
}