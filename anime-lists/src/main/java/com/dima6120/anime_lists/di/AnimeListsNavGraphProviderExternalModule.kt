package com.dima6120.anime_lists.di

import com.dima6120.anime_lists.AnimeListsScreenProviderImpl
import com.dima6120.anime_lists_api.AnimeListsRoute
import com.dima6120.core_api.di.RouteKey
import com.dima6120.core_api.navigation.ScreenProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface AnimeListsNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @RouteKey(AnimeListsRoute::class)
    fun provideAnimeListsNavGraphProvider(impl: AnimeListsScreenProviderImpl): ScreenProvider
}