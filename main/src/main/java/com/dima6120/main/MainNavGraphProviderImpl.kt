package com.dima6120.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dima6120.main.ui.MainScreen
import com.dima6120.main_api.MainNavGraphProvider
import com.dima6120.main_api.MainRoute
import javax.inject.Inject

class MainNavGraphProviderImpl @Inject constructor(): MainNavGraphProvider {

    override fun provideNavGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<MainRoute> {
            MainScreen(
                navBackStackEntry = it,
                navController = navController
            )
        }
    }
}