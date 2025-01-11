package com.dima6120.edit_anime_list_entry.di

import com.dima6120.edit_anime_list_entry.usecase.DeleteAnimeListEntryUseCase
import com.dima6120.edit_anime_list_entry.usecase.DeleteAnimeListEntryUseCaseImpl
import com.dima6120.edit_anime_list_entry.usecase.UpdateAnimeListEntryUseCase
import com.dima6120.edit_anime_list_entry.usecase.UpdateAnimeListEntryUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun provideUpdateAnimeListEntryUseCase(impl: UpdateAnimeListEntryUseCaseImpl): UpdateAnimeListEntryUseCase

    @Binds
    fun provideDeleteAnimeListEntryUseCase(impl: DeleteAnimeListEntryUseCaseImpl): DeleteAnimeListEntryUseCase
}