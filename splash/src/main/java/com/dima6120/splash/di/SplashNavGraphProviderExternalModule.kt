package com.dima6120.splash.di

import com.dima6120.core_api.navigation.NavGraphProvider
import com.dima6120.splash.SplashNavGraphProviderImpl
import com.dima6120.splash_api.SplashRoute
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface SplashNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @ClassKey(SplashRoute::class)
    fun provideSplashNavGraphProvider(impl: SplashNavGraphProviderImpl): NavGraphProvider
}