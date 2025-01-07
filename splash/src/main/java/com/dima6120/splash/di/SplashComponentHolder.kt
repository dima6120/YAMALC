package com.dima6120.splash.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder
import com.dima6120.splash_api.SplashRoute

internal object SplashComponentHolder: AbstractComponentHolder<SplashRoute, SplashComponent>() {

    override val componentName: String = "Splash"
    override fun createComponent(
        route: SplashRoute,
        applicationComponentProvider: ApplicationComponentProvider
    ): SplashComponent =
        SplashComponent.create(applicationComponentProvider)
}