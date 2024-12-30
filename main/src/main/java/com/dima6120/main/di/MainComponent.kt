package com.dima6120.main.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.navigation.NavGraphProvider
import dagger.Component
import javax.inject.Provider

@Component(
    dependencies = [ApplicationComponentProvider::class]
)
interface MainComponent {


    fun provideNavGraphProvidersMap(): Map<Class<*>, @JvmSuppressWildcards Provider<NavGraphProvider>>

    @Component.Factory
    interface Factory {

        fun createMainComponent(applicationComponentProvider: ApplicationComponentProvider): MainComponent
    }

    companion object {

        fun create(applicationComponentProvider: ApplicationComponentProvider): MainComponent =
            DaggerMainComponent.factory().createMainComponent(applicationComponentProvider)
    }
}