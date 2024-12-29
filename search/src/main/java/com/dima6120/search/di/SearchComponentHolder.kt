package com.dima6120.search.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder

internal object SearchComponentHolder: AbstractComponentHolder<SearchComponent>() {

    override val componentName: String = "Search"

    override fun createComponent(applicationComponentProvider: ApplicationComponentProvider): SearchComponent =
        DaggerSearchComponent.factory().createSearchComponent(applicationComponentProvider)
}