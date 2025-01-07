package com.dima6120.main.ui

import android.annotation.SuppressLint
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dima6120.main.di.MainComponent
import com.dima6120.main.di.MainComponentHolder
import com.dima6120.main.navigation.BottomNavItems
import com.dima6120.main_api.MainRoute
import com.dima6120.ui.Screen
import com.dima6120.ui.theme.YamalcColors

@Composable
fun MainScreen(
    id: String,
    lifecycle: Lifecycle,
    route: MainRoute,
    navController: NavHostController
) {
    Screen(
        id = id,
        lifecycle = lifecycle,
        route = route,
        componentHolder = MainComponentHolder
    ) {
        MainScreenInternal(
            mainComponent = it,
            lifecycle = lifecycle,
            navController = navController
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun MainScreenInternal(
    mainComponent: MainComponent,
    lifecycle: Lifecycle,
    navController: NavHostController
) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = YamalcColors.Gray78
            ) {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomNavItems.items.forEach { item ->
                    BottomNavigationItem(
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = YamalcColors.Gray78,
                        selected = currentDestination
                            ?.hierarchy
                            ?.any { it.hasRoute(item.route::class) } == true,
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = stringResource(id = item.title),
                            )
                        },
                        label = { Text(text = stringResource(id = item.title)) }
                    )
                }
            }
        }
    ) {
        val screenProviders = remember {
            val itemsRouteClasses = BottomNavItems.items.map { it.route::class.java }

            mainComponent.provideNavGraphProvidersMap()
                .filter { it.key in itemsRouteClasses }
                .map { it.value }
        }

        NavHost(
            navController = bottomNavController,
            route = MainRoute::class,
            startDestination = BottomNavItems.items.first().route
        ) {
            screenProviders.forEach { screenProvider ->
                screenProvider.get().provideDestination(
                    lifecycle = lifecycle,
                    navGraphBuilder = this,
                    navController = navController
                )
            }
        }
    }
}