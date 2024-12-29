package com.dima6120.profile.di

import com.dima6120.core_api.ApplicationComponentProvider
import dagger.Component

@Component(
    dependencies = [ApplicationComponentProvider::class]
)
interface ProfileComponent {

    @Component.Factory
    interface Factory {

        fun createProfileComponent(applicationComponentProvider: ApplicationComponentProvider): ProfileComponent
    }
}