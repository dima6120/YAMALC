package com.dima6120.edit_anime_list_entry.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder
import com.dima6120.edit_anime_list_entry_api.EditAnimeListEntryRoute

internal object EditAnimeListEntryComponentHolder
    : AbstractComponentHolder<EditAnimeListEntryRoute, EditAnimeListEntryComponent>() {

    override val componentName: String = "EditAnimeListEntry"

    override fun createComponent(
        route: EditAnimeListEntryRoute,
        applicationComponentProvider: ApplicationComponentProvider
    ): EditAnimeListEntryComponent =
        DaggerEditAnimeListEntryComponent.factory()
            .createEditAnimeListEntryComponent(
                animeBriefDetailsModel = route.animeBriefDetailsModel,
                myListStatusModel = route.myListStatusModel,
                applicationComponentProvider = applicationComponentProvider
            )
}