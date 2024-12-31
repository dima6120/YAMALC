package com.dima6120.core_impl.di

import com.dima6120.core_api.network.repository.ApiRepository
import com.dima6120.core_api.network.repository.LoginRepository
import com.dima6120.core_impl.network.repository.ApiRepositoryImpl
import com.dima6120.core_impl.network.repository.AuthRepository
import com.dima6120.core_impl.network.repository.AuthRepositoryImpl
import com.dima6120.core_impl.network.repository.TokensRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository


    @Singleton
    @Binds
    fun provideApiRepository(impl: ApiRepositoryImpl): ApiRepository

    companion object {

        @Singleton
        @Provides
        fun provideLoginRepository(authRepository: AuthRepository): LoginRepository = authRepository

        @Singleton
        @Provides
        fun provideTokensRepository(authRepository: AuthRepository): TokensRepository = authRepository
    }
}