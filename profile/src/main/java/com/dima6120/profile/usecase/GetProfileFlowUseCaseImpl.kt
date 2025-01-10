package com.dima6120.profile.usecase

import com.dima6120.core_api.model.profile.ProfileModel
import com.dima6120.core_api.network.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetProfileFlowUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository
): GetProfileFlowUseCase {
    override suspend fun invoke(): Flow<ProfileModel> =
        apiRepository.getProfileFlow().mapNotNull { it }
}