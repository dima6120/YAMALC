package com.dima6120.main.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.navigation.Route
import com.dima6120.core_api.navigation.ScreenProvider
import dagger.Component
import javax.inject.Provider
import kotlin.reflect.KClass

@Component(
    dependencies = [ApplicationComponentProvider::class]
)
interface MainComponent {


    fun provideNavGraphProvidersMap(): Map<Class<out Route>, @JvmSuppressWildcards Provider<ScreenProvider>>

    @Component.Factory
    interface Factory {

        fun createMainComponent(applicationComponentProvider: ApplicationComponentProvider): MainComponent
    }

    companion object {

        fun create(applicationComponentProvider: ApplicationComponentProvider): MainComponent =
            DaggerMainComponent.factory().createMainComponent(applicationComponentProvider)
    }
}