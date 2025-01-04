package com.dima6120.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.dima6120.anime_title_api.AnimeTitleRoute
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.search.di.SearchComponentHolder
import com.dima6120.search_api.SearchRoute
import com.dima6120.ui.Screen

@Composable
fun SearchScreen(
    id: String,
    lifecycle: Lifecycle,
    route: SearchRoute,
    navController: NavHostController
) {

    Screen(
        id = id,
        lifecycle = lifecycle,
        route = route,
        componentHolder = SearchComponentHolder
    ) {

        SearchScreenInternal(
            onButtonClick = {
                navController.navigate(
                    AnimeTitleRoute(AnimeId(813))
                )
            }
        )
    }
}

@Composable
private fun SearchScreenInternal(
    onButtonClick: () -> Unit
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = onButtonClick
        ) {
            Text(
                text = "Open AnimeTitle screen"
            )
        }
    }
}