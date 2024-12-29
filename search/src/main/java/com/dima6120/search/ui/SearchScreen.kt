package com.dima6120.search.ui

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
import com.dima6120.search.di.SearchComponentHolder
import com.dima6120.ui.ScreenWithComponent

@Composable
fun SearchScreen(
    navBackStackEntry: NavBackStackEntry,
    navController: NavHostController
) {

    ScreenWithComponent(
        navBackStackEntry = navBackStackEntry,
        componentHolder = SearchComponentHolder
    ) {

        SearchScreenInternal()
    }
}

@Composable
private fun SearchScreenInternal() {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            text = "Search"
        )
    }
}