package com.dima6120.core_api.network

import com.dima6120.core_api.network.repository.ApiRepository
import com.dima6120.core_api.network.repository.LoginRepository

interface NetworkProvider {

    fun provideAuthRepository(): LoginRepository

    fun provideApiRepository(): ApiRepository
}