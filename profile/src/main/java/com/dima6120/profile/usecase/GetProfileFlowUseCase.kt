package com.dima6120.profile.usecase

import com.dima6120.core_api.model.profile.ProfileModel
import kotlinx.coroutines.flow.Flow

interface GetProfileFlowUseCase {

    suspend operator fun invoke(): Flow<ProfileModel>
}