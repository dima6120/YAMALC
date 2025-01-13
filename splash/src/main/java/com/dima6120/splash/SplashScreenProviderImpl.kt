package com.dima6120.splash

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dima6120.splash.ui.SplashScreen
import com.dima6120.splash_api.SplashRoute
import javax.inject.Inject

class SplashScreenProviderImpl @Inject constructor(): SplashScreenProvider {

    override fun provideDestination(
        lifecycle: Lifecycle?,
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<SplashRoute> {
            SplashScreen(
                id = it.id,
                lifecycle = lifecycle ?: it.lifecycle,
                route = it.toRoute(),
                navController = navController
            )
        }
    }
}