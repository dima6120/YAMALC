package com.dima6120.search.di

import com.dima6120.search.usecase.GetAnimeListUseCase
import com.dima6120.search.usecase.GetAnimeListUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun provideGetAnimeListUseCase(impl: GetAnimeListUseCaseImpl): GetAnimeListUseCase
}