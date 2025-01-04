package com.dima6120.search.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder
import com.dima6120.search_api.SearchRoute

internal object SearchComponentHolder: AbstractComponentHolder<SearchRoute, SearchComponent>() {

    override val componentName: String = "Search"

    override fun createComponent(
        route: SearchRoute,
        applicationComponentProvider: ApplicationComponentProvider
    ): SearchComponent =
        DaggerSearchComponent.factory().createSearchComponent(applicationComponentProvider)
}