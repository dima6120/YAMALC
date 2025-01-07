package com.dima6120.profile.usecase

import com.dima6120.core_api.di.DispatcherIO
import com.dima6120.core_api.error.ErrorHandler
import com.dima6120.core_api.model.profile.ProfileModel
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.network.repository.ApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProfileUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val errorHandler: ErrorHandler,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
): GetProfileUseCase {

    override suspend fun invoke(): UseCaseResult<ProfileModel> =
        withContext(ioDispatcher) {
            errorHandler.run {
                apiRepository.getProfile()
            }
        }
}