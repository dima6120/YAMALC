package com.dima6120.search.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.search.ui.SearchScreenViewModel
import dagger.Component

@Component(
    dependencies = [ApplicationComponentProvider::class],
    modules = [UseCaseModule::class]
)
interface SearchComponent {

    fun provideSearchScreenViewModelFactory(): SearchScreenViewModel.Factory

    @Component.Factory
    interface Factory {

        fun createSearchComponent(applicationComponentProvider: ApplicationComponentProvider): SearchComponent
    }
}