package com.dima6120.splash.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder

internal object SplashComponentHolder: AbstractComponentHolder<SplashComponent>() {

    override val componentName: String = "Splash"
    override fun createComponent(applicationComponentProvider: ApplicationComponentProvider): SplashComponent =
        SplashComponent.create(applicationComponentProvider)
}