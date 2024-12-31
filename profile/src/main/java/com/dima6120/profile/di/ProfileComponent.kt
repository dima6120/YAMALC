package com.dima6120.profile.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.profile.ui.ProfileViewModel
import dagger.Component

@Component(
    dependencies = [ApplicationComponentProvider::class],
    modules = [UseCaseModule::class]
)
interface ProfileComponent {

    fun provideProfileViewModelFactory(): ProfileViewModel.Factory

    @Component.Factory
    interface Factory {

        fun createProfileComponent(applicationComponentProvider: ApplicationComponentProvider): ProfileComponent
    }
}