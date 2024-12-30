package com.dima6120.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dima6120.search.ui.SearchScreen
import com.dima6120.search_api.SearchNavGraphProvider
import com.dima6120.search_api.SearchRoute
import javax.inject.Inject

class SearchNavGraphProviderImpl @Inject constructor(): SearchNavGraphProvider {

    override fun provideNavGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<SearchRoute> {
            SearchScreen(
                navBackStackEntry = it,
                navController = navController
            )
        }
    }
}