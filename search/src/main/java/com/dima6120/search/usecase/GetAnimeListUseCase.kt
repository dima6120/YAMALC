package com.dima6120.search.usecase

import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel

interface GetAnimeListUseCase {

    suspend operator fun invoke(query: String, page: Int = 0): UseCaseResult<List<AnimeBriefDetailsModel>>
}