package com.dima6120.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.dima6120.main.di.MainComponentHolder
import com.dima6120.ui.ScreenWithComponent

@Composable
fun MainScreen(
    navBackStackEntry: NavBackStackEntry,
    navController: NavHostController
) {
    ScreenWithComponent(
        navBackStackEntry = navBackStackEntry,
        componentHolder = MainComponentHolder
    ) {
        MainScreenInternal()
    }
}

@Composable
private fun MainScreenInternal() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            text = "Main"
        )
    }
}