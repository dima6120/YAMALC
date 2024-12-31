package com.dima6120.profile.usecase

import kotlinx.coroutines.flow.Flow

interface GetLoggedInFlowUseCase {

    suspend operator fun invoke(): Flow<Boolean>
}