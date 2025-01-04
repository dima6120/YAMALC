package com.dima6120.search.di

import com.dima6120.core_api.di.RouteKey
import com.dima6120.core_api.navigation.ScreenProvider
import com.dima6120.search.SearchScreenProviderImpl
import com.dima6120.search_api.SearchRoute
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface SearchNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @RouteKey(SearchRoute::class)
    fun provideSearchNavGraphProvider(impl: SearchScreenProviderImpl): ScreenProvider
}