package com.dima6120.splash.di

import com.dima6120.core_api.navigation.NavGraphProvider
import com.dima6120.splash.SplashNavGraphProviderImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
interface SplashNavGraphProviderExternalModule {

    @Binds
    @IntoSet
    fun provideSplashNavGraphProvider(impl: SplashNavGraphProviderImpl): NavGraphProvider
}