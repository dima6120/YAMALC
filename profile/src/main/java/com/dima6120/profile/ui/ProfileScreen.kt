package com.dima6120.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.dima6120.profile.di.ProfileComponentHolder
import com.dima6120.ui.ScreenWithComponent

@Composable
fun ProfileScreen(
    navBackStackEntry: NavBackStackEntry,
    navController: NavHostController
) {

    ScreenWithComponent(
        navBackStackEntry = navBackStackEntry,
        componentHolder = ProfileComponentHolder
    ) {

        ProfileScreenInternal()
    }
}

@Composable
private fun ProfileScreenInternal() {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            text = "Profile"
        )
    }
}