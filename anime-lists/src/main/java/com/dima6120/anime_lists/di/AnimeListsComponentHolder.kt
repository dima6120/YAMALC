package com.dima6120.anime_lists.di

import com.dima6120.anime_lists_api.AnimeListsRoute
import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder

internal object AnimeListsComponentHolder: AbstractComponentHolder<AnimeListsRoute, AnimeListsComponent>() {

    override val componentName: String = "AnimeLists"

    override fun createComponent(
        route: AnimeListsRoute,
        applicationComponentProvider: ApplicationComponentProvider
    ): AnimeListsComponent =
        DaggerAnimeListsComponent.factory().createAnimeListsComponent(applicationComponentProvider)
}