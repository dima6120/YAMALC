package com.dima6120.anime_lists.usecase

import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.core_api.model.mylist.MyListAnimeModel

interface GetMyAnimeListUseCase {

    suspend operator fun invoke(status: ListStatusModel, page: Int = 0): UseCaseResult<List<MyListAnimeModel>>
}