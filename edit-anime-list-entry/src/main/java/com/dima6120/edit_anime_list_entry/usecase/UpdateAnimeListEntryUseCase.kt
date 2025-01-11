package com.dima6120.edit_anime_list_entry.usecase

import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.mylist.AnimeListEntryUpdateModel

interface UpdateAnimeListEntryUseCase {

    suspend operator fun invoke(animeListEntryUpdateModel: AnimeListEntryUpdateModel): UseCaseResult<Unit>
}