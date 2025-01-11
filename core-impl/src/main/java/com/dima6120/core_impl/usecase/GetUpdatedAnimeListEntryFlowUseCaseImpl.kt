package com.dima6120.core_impl.usecase

import com.dima6120.core_api.model.mylist.AnimeListEntryUpdateModel
import com.dima6120.core_api.network.repository.ApiRepository
import com.dima6120.core_api.usecase.GetUpdatedAnimeListEntryFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpdatedAnimeListEntryFlowUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository
): GetUpdatedAnimeListEntryFlowUseCase {

    override fun invoke(): Flow<AnimeListEntryUpdateModel> =
        apiRepository.getUpdatedAnimeListEntryFlow()
}