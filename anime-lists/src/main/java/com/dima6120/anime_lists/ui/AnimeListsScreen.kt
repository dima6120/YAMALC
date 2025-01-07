package com.dima6120.anime_lists.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.dima6120.anime_lists.di.AnimeListsComponentHolder
import com.dima6120.anime_lists_api.AnimeListsRoute
import com.dima6120.ui.Screen

@Composable
fun AnimeListsScreen(
    id: String,
    lifecycle: Lifecycle,
    route: AnimeListsRoute,
    navController: NavHostController
) {

    Screen(
        id = id,
        lifecycle = lifecycle,
        route = route,
        componentHolder = AnimeListsComponentHolder
    ) {

        AnimeListsScreenInternal()
    }
}

@Composable
private fun AnimeListsScreenInternal() {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            text = "AnimeLists"
        )
    }
}