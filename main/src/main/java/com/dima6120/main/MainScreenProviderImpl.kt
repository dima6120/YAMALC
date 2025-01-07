package com.dima6120.main

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dima6120.main.ui.MainScreen
import com.dima6120.main_api.MainScreenProvider
import com.dima6120.main_api.MainRoute
import javax.inject.Inject

class MainScreenProviderImpl @Inject constructor(): MainScreenProvider {

    override fun provideDestination(
        lifecycle: Lifecycle?,
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<MainRoute> {
            MainScreen(
                id = it.id,
                lifecycle = lifecycle ?: it.lifecycle,
                route = it.toRoute(),
                navController = navController
            )
        }
    }
}