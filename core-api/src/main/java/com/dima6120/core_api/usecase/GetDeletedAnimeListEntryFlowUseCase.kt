package com.dima6120.core_api.usecase

import com.dima6120.core_api.model.anime.AnimeId
import kotlinx.coroutines.flow.Flow

interface GetDeletedAnimeListEntryFlowUseCase {

    operator fun invoke(): Flow<AnimeId>
}