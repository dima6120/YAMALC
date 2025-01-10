package com.dima6120.anime_lists.di

import com.dima6120.anime_lists.ui.AnimeListsViewModel
import com.dima6120.core_api.ApplicationComponentProvider
import dagger.Component

@Component(
    dependencies = [ApplicationComponentProvider::class],
    modules = [UseCaseModule::class]
)
interface AnimeListsComponent {

    fun providerAnimeListsViewModelFactory(): AnimeListsViewModel.Factory

    @Component.Factory
    interface Factory {

        fun createAnimeListsComponent(applicationComponentProvider: ApplicationComponentProvider): AnimeListsComponent
    }
}