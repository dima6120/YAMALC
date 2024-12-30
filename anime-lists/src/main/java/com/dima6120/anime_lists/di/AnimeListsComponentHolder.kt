package com.dima6120.anime_lists.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder

internal object AnimeListsComponentHolder: AbstractComponentHolder<AnimeListsComponent>() {

    override val componentName: String = "AnimeLists"

    override fun createComponent(applicationComponentProvider: ApplicationComponentProvider): AnimeListsComponent =
        DaggerAnimeListsComponent.factory().createAnimeListsComponent(applicationComponentProvider)
}