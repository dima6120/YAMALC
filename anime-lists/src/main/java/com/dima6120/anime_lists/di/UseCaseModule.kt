package com.dima6120.anime_lists.di

import com.dima6120.anime_lists.usecase.GetAnimeStatisticsUseCase
import com.dima6120.anime_lists.usecase.GetAnimeStatisticsUseCaseImpl
import com.dima6120.anime_lists.usecase.GetMyAnimeListUseCase
import com.dima6120.anime_lists.usecase.GetMyAnimeListUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun provideGetAnimeStatisticsUseCase(impl: GetAnimeStatisticsUseCaseImpl): GetAnimeStatisticsUseCase

    @Binds
    fun providerGetMyAnimeListUseCase(impl: GetMyAnimeListUseCaseImpl): GetMyAnimeListUseCase
}