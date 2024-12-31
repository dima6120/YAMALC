package com.dima6120.yamalc.usecase

import com.dima6120.core_api.model.UseCaseResult

interface GetTokenUseCase {

    suspend operator fun invoke(code: String): UseCaseResult<Unit>
}