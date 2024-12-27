package com.dima6120.main.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder

internal object MainComponentHolder: AbstractComponentHolder<MainComponent>() {

    override val componentName: String = "Main"

    override fun createComponent(applicationComponentProvider: ApplicationComponentProvider): MainComponent =
        MainComponent.create(applicationComponentProvider)
}