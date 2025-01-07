package com.dima6120.profile.usecase

import com.dima6120.core_api.model.profile.ProfileModel
import com.dima6120.core_api.model.UseCaseResult

interface GetProfileUseCase {

    suspend operator fun invoke(): UseCaseResult<ProfileModel>
}