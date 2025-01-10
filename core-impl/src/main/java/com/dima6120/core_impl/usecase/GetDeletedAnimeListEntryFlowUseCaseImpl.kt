package com.dima6120.core_impl.usecase

import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.network.repository.ApiRepository
import com.dima6120.core_api.usecase.GetDeletedAnimeListEntryFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDeletedAnimeListEntryFlowUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository
): GetDeletedAnimeListEntryFlowUseCase {

    override fun invoke(): Flow<AnimeId> = apiRepository.getDeletedAnimeListEntryFlow()
}