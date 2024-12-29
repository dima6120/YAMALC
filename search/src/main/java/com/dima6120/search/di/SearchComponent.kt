package com.dima6120.search.di

import com.dima6120.core_api.ApplicationComponentProvider
import dagger.Component

@Component(
    dependencies = [ApplicationComponentProvider::class]
)
interface SearchComponent {

    @Component.Factory
    interface Factory {

        fun createSearchComponent(applicationComponentProvider: ApplicationComponentProvider): SearchComponent
    }
}