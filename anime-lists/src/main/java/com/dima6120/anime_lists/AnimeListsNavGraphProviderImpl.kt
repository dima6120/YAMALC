package com.dima6120.anime_lists

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dima6120.anime_lists.ui.AnimeListsScreen
import com.dima6120.anime_lists_api.AnimeListsNavGraphProvider
import com.dima6120.anime_lists_api.AnimeListsRoute
import javax.inject.Inject

class AnimeListsNavGraphProviderImpl @Inject constructor(): AnimeListsNavGraphProvider {

    override fun provideNavGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<AnimeListsRoute> {
            AnimeListsScreen(
                navBackStackEntry = it,
                navController = navController
            )
        }
    }
}