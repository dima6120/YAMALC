package com.dima6120.edit_anime_list_entry.di

import com.dima6120.core_api.di.RouteKey
import com.dima6120.core_api.navigation.ScreenProvider
import com.dima6120.edit_anime_list_entry.EditAnimeListEntryScreenProvider
import com.dima6120.edit_anime_list_entry.EditAnimeListEntryScreenProviderImpl
import com.dima6120.edit_anime_list_entry_api.EditAnimeListEntryRoute
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EditAnimeListEntryNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @RouteKey(EditAnimeListEntryRoute::class)
    fun provideEditAnimeListEntryNavGraphProvider(
        impl: EditAnimeListEntryScreenProviderImpl
    ): ScreenProvider
}