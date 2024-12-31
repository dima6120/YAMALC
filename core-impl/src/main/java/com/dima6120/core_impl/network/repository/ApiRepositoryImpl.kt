package com.dima6120.core_impl.network.repository

import com.dima6120.core_api.model.ProfileModel
import com.dima6120.core_api.network.repository.ApiRepository
import com.dima6120.core_impl.error.InternalErrorHandler
import com.dima6120.core_impl.network.model.user.toProfileModel
import com.dima6120.core_impl.network.service.ApiService
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val internalErrorHandler: InternalErrorHandler
): ApiRepository {

    override suspend fun getProfile(): ProfileModel =
        internalErrorHandler.run {
            apiService.getProfile().toProfileModel()
        }
}