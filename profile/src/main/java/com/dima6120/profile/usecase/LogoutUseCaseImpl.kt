package com.dima6120.profile.usecase

import com.dima6120.core_api.di.DispatcherIO
import com.dima6120.core_api.network.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
): LogoutUseCase {

    override suspend fun invoke() =
        withContext(ioDispatcher) {
            loginRepository.deleteAuthToken()
        }
}