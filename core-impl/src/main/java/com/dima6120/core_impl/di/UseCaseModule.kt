package com.dima6120.core_impl.di

import com.dima6120.core_api.usecase.GetAuthorizeLinkUseCase
import com.dima6120.core_api.usecase.GetLoggedInFlowUseCase
import com.dima6120.core_impl.usecase.GetAuthorizeLinkUseCaseImpl
import com.dima6120.core_impl.usecase.GetLoggedInFlowUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun provideGetAuthorizeLinkUseCase(impl: GetAuthorizeLinkUseCaseImpl): GetAuthorizeLinkUseCase

    @Binds
    fun provideGetLoggedInFlowUseCase(impl: GetLoggedInFlowUseCaseImpl): GetLoggedInFlowUseCase

}