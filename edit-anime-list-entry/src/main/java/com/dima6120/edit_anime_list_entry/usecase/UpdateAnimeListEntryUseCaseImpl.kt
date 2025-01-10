package com.dima6120.edit_anime_list_entry.usecase

import com.dima6120.core_api.di.DispatcherIO
import com.dima6120.core_api.error.ErrorHandler
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.mylist.AnimeListEntryUpdateModel
import com.dima6120.core_api.network.repository.ApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateAnimeListEntryUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val errorHandler: ErrorHandler,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
): UpdateAnimeListEntryUseCase {

    override suspend fun invoke(
        animeListEntryUpdateModel: AnimeListEntryUpdateModel
    ): UseCaseResult<Unit> =
        withContext(ioDispatcher) {
            errorHandler.run {
                apiRepository.updateAnimeListEntry(animeListEntryUpdateModel)
            }
        }
}