package com.dima6120.core_api.network.repository

import com.dima6120.core_api.model.ProfileModel

interface ApiRepository {

    suspend fun getProfile(): ProfileModel
}