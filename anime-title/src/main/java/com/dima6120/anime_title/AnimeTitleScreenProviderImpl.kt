package com.dima6120.anime_title

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dima6120.anime_title.ui.AnimeTitleScreen
import com.dima6120.anime_title_api.AnimeTitleRoute
import com.dima6120.core_api.navigation.CustomNavTypes
import javax.inject.Inject

class AnimeTitleScreenProviderImpl @Inject constructor(): AnimeTitleScreenProvider {

    override fun provideDestination(
        lifecycle: Lifecycle?,
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<AnimeTitleRoute>(
            typeMap = mapOf(CustomNavTypes.AnimeIdTypeEntry)
        ) {
            AnimeTitleScreen(
                id = it.id,
                lifecycle = lifecycle ?: it.lifecycle,
                route = it.toRoute(),
                navController = navController
            )
        }
    }
}