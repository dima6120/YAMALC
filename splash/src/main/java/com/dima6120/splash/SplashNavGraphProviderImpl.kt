package com.dima6120.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dima6120.splash.ui.SplashScreen
import com.dima6120.splash_api.SplashNavGraphProvider
import com.dima6120.splash_api.SplashRoute
import javax.inject.Inject

class SplashNavGraphProviderImpl @Inject constructor(): SplashNavGraphProvider {

    override fun provideNavGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<SplashRoute> {
            SplashScreen(navBackStackEntry = it, navController = navController)
        }
    }
}