package com.dima6120.core_impl.di

import com.dima6120.core_api.usecase.GetAuthorizeLinkUseCase
import com.dima6120.core_api.usecase.GetDeletedAnimeListEntryFlowUseCase
import com.dima6120.core_api.usecase.GetLoggedInFlowUseCase
import com.dima6120.core_api.usecase.GetUpdatedAnimeListEntryFlowUseCase
import com.dima6120.core_impl.usecase.GetAuthorizeLinkUseCaseImpl
import com.dima6120.core_impl.usecase.GetDeletedAnimeListEntryFlowUseCaseImpl
import com.dima6120.core_impl.usecase.GetLoggedInFlowUseCaseImpl
import com.dima6120.core_impl.usecase.GetUpdatedAnimeListEntryFlowUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun provideGetAuthorizeLinkUseCase(impl: GetAuthorizeLinkUseCaseImpl): GetAuthorizeLinkUseCase

    @Binds
    fun provideGetLoggedInFlowUseCase(impl: GetLoggedInFlowUseCaseImpl): GetLoggedInFlowUseCase

    @Binds
    fun provideGetUpdatedAnimeListEntryFlowUseCase(
        impl: GetUpdatedAnimeListEntryFlowUseCaseImpl
    ): GetUpdatedAnimeListEntryFlowUseCase

    @Binds
    fun provideGetDeletedAnimeListEntryFlowUseCase(
        impl: GetDeletedAnimeListEntryFlowUseCaseImpl
    ): GetDeletedAnimeListEntryFlowUseCase
}