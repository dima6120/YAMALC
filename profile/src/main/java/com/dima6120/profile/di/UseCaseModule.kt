package com.dima6120.profile.di

import com.dima6120.profile.usecase.GetAuthorizeLinkUseCase
import com.dima6120.profile.usecase.GetAuthorizeLinkUseCaseImpl
import com.dima6120.profile.usecase.GetLoggedInFlowUseCase
import com.dima6120.profile.usecase.GetLoggedInFlowUseCaseImpl
import com.dima6120.profile.usecase.GetProfileUseCase
import com.dima6120.profile.usecase.GetProfileUseCaseImpl
import com.dima6120.profile.usecase.LogoutUseCase
import com.dima6120.profile.usecase.LogoutUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun provideGetAuthorizeLinkUseCase(impl: GetAuthorizeLinkUseCaseImpl): GetAuthorizeLinkUseCase

    @Binds
    fun provideGetLoggedInFlowUseCase(impl: GetLoggedInFlowUseCaseImpl): GetLoggedInFlowUseCase

    @Binds
    fun provideGetProfileUseCase(impl: GetProfileUseCaseImpl): GetProfileUseCase

    @Binds
    fun provideLogoutUseCase(impl: LogoutUseCaseImpl): LogoutUseCase
}