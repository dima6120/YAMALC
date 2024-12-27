package com.dima6120.main.di

import com.dima6120.core_api.ApplicationComponentProvider
import dagger.Component

@Component(
    dependencies = [ApplicationComponentProvider::class]
)
interface MainComponent {

    companion object {

        fun create(applicationComponentProvider: ApplicationComponentProvider): MainComponent =
            DaggerMainComponent.builder()
                .applicationComponentProvider(applicationComponentProvider)
                .build()
    }
}