package com.dima6120.splash.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.Feature
import com.dima6120.splash.ui.SplashViewModel
import dagger.Component

@Feature
@Component(
    dependencies = [ApplicationComponentProvider::class]
)
interface SplashComponent {

    fun provideSplashViewModelFactory(): SplashViewModel.Factory

    @Component.Factory
    interface Factory {

        fun createSplashComponent(applicationComponentProvider: ApplicationComponentProvider): SplashComponent
    }

    companion object {

        fun create(applicationComponentProvider: ApplicationComponentProvider): SplashComponent =
            DaggerSplashComponent.factory().createSplashComponent(applicationComponentProvider)
    }
}