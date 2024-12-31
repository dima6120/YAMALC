package com.dima6120.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import com.dima6120.core_api.di.ComponentHolder

@Composable
fun <T> ScreenWithComponent(
    navBackStackEntry: NavBackStackEntry,
    componentHolder: ComponentHolder<T>,
    content: @Composable (T) -> Unit
) {
    val applicationComponentProvider = LocalApplicationComponentProvider.current

    val component = remember {
        componentHolder.create(
            id = navBackStackEntry.id,
            lifecycle = navBackStackEntry.lifecycle,
            applicationComponentProvider = applicationComponentProvider
        )
    }

    content(component)
}