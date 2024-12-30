package com.dima6120.search.di

import com.dima6120.core_api.navigation.NavGraphProvider
import com.dima6120.search.SearchNavGraphProviderImpl
import com.dima6120.search_api.SearchRoute
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface SearchNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @ClassKey(SearchRoute::class)
    fun provideSearchNavGraphProvider(impl: SearchNavGraphProviderImpl): NavGraphProvider
}