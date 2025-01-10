package com.dima6120.core_impl.usecase

import com.dima6120.core_api.network.repository.LoginRepository
import com.dima6120.core_api.usecase.GetLoggedInFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedInFlowUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : GetLoggedInFlowUseCase {

    override suspend fun invoke(): Flow<Boolean> = loginRepository.getLoggedInFlow()
}