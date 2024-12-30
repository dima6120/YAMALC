package com.dima6120.main.di

import com.dima6120.core_api.navigation.NavGraphProvider
import com.dima6120.main.MainNavGraphProviderImpl
import com.dima6120.main_api.MainRoute
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface MainNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @ClassKey(MainRoute::class)
    fun provideMainNavGraphProvider(impl: MainNavGraphProviderImpl): NavGraphProvider
}