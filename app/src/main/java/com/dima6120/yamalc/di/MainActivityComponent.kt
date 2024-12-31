package com.dima6120.yamalc.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.yamalc.ui.MainActivity
import dagger.Component


@Component(
    dependencies = [ApplicationComponentProvider::class],
    modules = [UseCaseModule::class]
)
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {

        fun createMainActivityComponent(applicationComponentProvider: ApplicationComponentProvider): MainActivityComponent
    }

    companion object {

        fun create(applicationComponentProvider: ApplicationComponentProvider): MainActivityComponent =
            DaggerMainActivityComponent.factory().createMainActivityComponent(applicationComponentProvider)
    }
}