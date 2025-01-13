package com.dima6120.anime_lists

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dima6120.anime_lists.ui.AnimeListsScreen
import com.dima6120.anime_lists_api.AnimeListsRoute
import javax.inject.Inject

class AnimeListsScreenProviderImpl @Inject constructor(): AnimeListsScreenProvider {

    override fun provideDestination(
        lifecycle: Lifecycle?,
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<AnimeListsRoute> {
            AnimeListsScreen(
                id = it.id,
                lifecycle = lifecycle ?: it.lifecycle,
                route = it.toRoute(),
                navController = navController
            )
        }
    }
}