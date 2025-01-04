package com.dima6120.profile.usecase

import com.dima6120.core_api.network.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLoggedInFlowUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : GetLoggedInFlowUseCase {

    override suspend fun invoke(): Flow<Boolean> = loginRepository.getLoggedInFlow()
}