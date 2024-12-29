package com.dima6120.splash.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.dima6120.main_api.MainRoute
import com.dima6120.splash.di.SplashComponentHolder
import com.dima6120.splash_api.SplashRoute
import com.dima6120.ui.ScreenWithComponent
import de.palm.composestateevents.EventEffect

@Composable
fun SplashScreen(
    navBackStackEntry: NavBackStackEntry,
    navController: NavHostController
) {

    ScreenWithComponent(
        navBackStackEntry = navBackStackEntry,
        componentHolder = SplashComponentHolder
    ) {
        val viewModel = viewModel<SplashViewModel>(factory = it.provideSplashViewModelFactory())

        EventEffect(
            event = viewModel.state.navigateToMainRouteEvent,
            onConsumed = viewModel::navigateToMainRouteEventConsumed
        ) {
            navController.navigate(MainRoute) {
                popUpTo(SplashRoute) {
                    inclusive = true
                }
            }
        }

        SplashScreenInternal()
    }
}

@Composable
private fun SplashScreenInternal() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            text = "Splash"
        )
    }

}