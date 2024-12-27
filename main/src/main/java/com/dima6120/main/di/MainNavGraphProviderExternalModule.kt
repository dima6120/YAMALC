package com.dima6120.main.di

import com.dima6120.core_api.navigation.NavGraphProvider
import com.dima6120.main.MainNavGraphProviderImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
interface MainNavGraphProviderExternalModule {

    @Binds
    @IntoSet
    fun provideMainNavGraphProvider(impl: MainNavGraphProviderImpl): NavGraphProvider
}