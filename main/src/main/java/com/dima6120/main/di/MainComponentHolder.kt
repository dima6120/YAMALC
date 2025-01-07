package com.dima6120.main.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder
import com.dima6120.main_api.MainRoute

internal object MainComponentHolder: AbstractComponentHolder<MainRoute, MainComponent>() {

    override val componentName: String = "Main"

    override fun createComponent(
        route: MainRoute,
        applicationComponentProvider: ApplicationComponentProvider
    ): MainComponent =
        MainComponent.create(applicationComponentProvider)
}