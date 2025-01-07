package com.dima6120.splash.di

import com.dima6120.core_api.di.RouteKey
import com.dima6120.core_api.navigation.ScreenProvider
import com.dima6120.splash.SplashScreenProviderImpl
import com.dima6120.splash_api.SplashRoute
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface SplashNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @RouteKey(SplashRoute::class)
    fun provideSplashNavGraphProvider(impl: SplashScreenProviderImpl): ScreenProvider
}