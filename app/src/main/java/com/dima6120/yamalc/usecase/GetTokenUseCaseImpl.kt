package com.dima6120.yamalc.usecase

import com.dima6120.core_api.di.DispatcherIO
import com.dima6120.core_api.error.ErrorHandler
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.network.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTokenUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository,
    private val errorHandler: ErrorHandler,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
): GetTokenUseCase {

    override suspend fun invoke(code: String): UseCaseResult<Unit> =
        withContext(ioDispatcher) {
            errorHandler.run {
                loginRepository.getAuthToken(code)
            }
        }

}