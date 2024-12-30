package com.dima6120.anime_lists.di

import com.dima6120.core_api.ApplicationComponentProvider
import dagger.Component

@Component(
    dependencies = [ApplicationComponentProvider::class]
)
interface AnimeListsComponent {

    @Component.Factory
    interface Factory {

        fun createAnimeListsComponent(applicationComponentProvider: ApplicationComponentProvider): AnimeListsComponent
    }
}