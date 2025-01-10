package com.dima6120.edit_anime_list_entry.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel
import com.dima6120.core_api.model.mylist.MyListStatusModel
import com.dima6120.edit_anime_list_entry.ui.EditAnimeListEntryViewModel
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [ApplicationComponentProvider::class],
    modules = [UseCaseModule::class]
)
interface EditAnimeListEntryComponent {

    fun provideEditAnimeListEntryViewModelFactory(): EditAnimeListEntryViewModel.Factory

    @Component.Factory
    interface Factory {

        fun createEditAnimeListEntryComponent(
            @BindsInstance animeBriefDetailsModel: AnimeBriefDetailsModel,
            @BindsInstance myListStatusModel: MyListStatusModel,
            applicationComponentProvider: ApplicationComponentProvider
        ): EditAnimeListEntryComponent
    }
}