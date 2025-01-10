package com.dima6120.core_api.usecase

import kotlinx.coroutines.flow.Flow

interface GetLoggedInFlowUseCase {

    suspend operator fun invoke(): Flow<Boolean>
}