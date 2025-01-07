package com.dima6120.profile.di

import com.dima6120.core_api.di.RouteKey
import com.dima6120.core_api.navigation.ScreenProvider
import com.dima6120.profile.ProfileScreenProviderImpl
import com.dima6120.profile_api.ProfileRoute
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface ProfileNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @RouteKey(ProfileRoute::class)
    fun provideProfileNavGraphProvider(impl: ProfileScreenProviderImpl): ScreenProvider
}