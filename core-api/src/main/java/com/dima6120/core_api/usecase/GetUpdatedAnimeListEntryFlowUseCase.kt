package com.dima6120.core_api.usecase

import com.dima6120.core_api.model.mylist.AnimeListEntryUpdateModel
import kotlinx.coroutines.flow.Flow

interface GetUpdatedAnimeListEntryFlowUseCase {

    operator fun invoke(): Flow<AnimeListEntryUpdateModel>
}