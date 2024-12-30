package com.dima6120.anime_lists.di

import com.dima6120.anime_lists.AnimeListsNavGraphProviderImpl
import com.dima6120.anime_lists_api.AnimeListsRoute
import com.dima6120.core_api.navigation.NavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface AnimeListsNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @ClassKey(AnimeListsRoute::class)
    fun provideAnimeListsNavGraphProvider(impl: AnimeListsNavGraphProviderImpl): NavGraphProvider
}