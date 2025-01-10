package com.dima6120.profile.di

import com.dima6120.profile.usecase.GetProfileUseCase
import com.dima6120.profile.usecase.GetProfileUseCaseImpl
import com.dima6120.profile.usecase.LogoutUseCase
import com.dima6120.profile.usecase.LogoutUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun provideGetProfileUseCase(impl: GetProfileUseCaseImpl): GetProfileUseCase

    @Binds
    fun provideLogoutUseCase(impl: LogoutUseCaseImpl): LogoutUseCase
}