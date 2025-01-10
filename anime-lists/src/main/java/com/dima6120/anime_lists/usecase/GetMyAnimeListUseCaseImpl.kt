package com.dima6120.anime_lists.usecase

import com.dima6120.core_api.di.DispatcherIO
import com.dima6120.core_api.error.ErrorHandler
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel
import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.core_api.model.mylist.MyListAnimeModel
import com.dima6120.core_api.network.repository.ApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMyAnimeListUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val errorHandler: ErrorHandler,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
): GetMyAnimeListUseCase {

    override suspend fun invoke(
        status: ListStatusModel,
        page: Int
    ): UseCaseResult<List<MyListAnimeModel>> =
        withContext(ioDispatcher) {
            errorHandler.run {
                apiRepository.getMyAnimeList(
                    status = status,
                    page = page
                )
            }
        }
}