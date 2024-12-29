package com.dima6120.profile.di

import com.dima6120.core_api.navigation.NavGraphProvider
import com.dima6120.profile.ProfileNavGraphProviderImpl
import com.dima6120.profile_api.ProfileRoute
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface ProfileNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @ClassKey(ProfileRoute::class)
    fun provideProfileNavGraphProvider(impl: ProfileNavGraphProviderImpl): NavGraphProvider
}