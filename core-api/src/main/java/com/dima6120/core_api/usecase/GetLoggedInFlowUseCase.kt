package com.dima6120.core_api.usecase

import kotlinx.coroutines.flow.Flow

interface GetLoggedInFlowUseCase {

    operator fun invoke(): Flow<Boolean>
}