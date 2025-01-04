package com.dima6120.anime_title.di

import com.dima6120.anime_title.usecase.GetAnimeDetailsUseCase
import com.dima6120.anime_title.usecase.GetAnimeDetailsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun provideGetAnimeDetailsUseCase(impl: GetAnimeDetailsUseCaseImpl): GetAnimeDetailsUseCase
}