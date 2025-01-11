package com.dima6120.edit_anime_list_entry

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dima6120.core_api.navigation.CustomNavTypes
import com.dima6120.edit_anime_list_entry.ui.EditAnimeListEntryScreen
import com.dima6120.edit_anime_list_entry_api.EditAnimeListEntryRoute
import javax.inject.Inject

class EditAnimeListEntryScreenProviderImpl @Inject constructor(): EditAnimeListEntryScreenProvider {

    override fun provideDestination(
        lifecycle: Lifecycle?,
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<EditAnimeListEntryRoute>(
            typeMap = mapOf(
                CustomNavTypes.MyListStatusModelTypeEntry,
                CustomNavTypes.AnimeBriefDetailsModelTypeEntry
            )
        ) {
            EditAnimeListEntryScreen(
                id = it.id,
                lifecycle = lifecycle ?: it.lifecycle,
                route = it.toRoute(),
                navController = navController
            )
        }
    }
}