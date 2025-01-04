package com.dima6120.search

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dima6120.search.ui.SearchScreen
import com.dima6120.search_api.SearchScreenProvider
import com.dima6120.search_api.SearchRoute
import javax.inject.Inject

class SearchScreenProviderImpl @Inject constructor(): SearchScreenProvider {

    override fun provideDestination(
        lifecycle: Lifecycle?,
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<SearchRoute> {
            SearchScreen(
                id = it.id,
                lifecycle = lifecycle ?: it.lifecycle,
                route = it.toRoute(),
                navController = navController
            )
        }
    }
}