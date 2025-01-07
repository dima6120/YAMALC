package com.dima6120.main.di

import com.dima6120.core_api.di.RouteKey
import com.dima6120.core_api.navigation.ScreenProvider
import com.dima6120.main.MainScreenProviderImpl
import com.dima6120.main_api.MainRoute
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface MainNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @RouteKey(MainRoute::class)
    fun provideMainNavGraphProvider(impl: MainScreenProviderImpl): ScreenProvider
}