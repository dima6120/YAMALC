package com.dima6120.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import com.dima6120.core_api.di.ComponentHolder
import com.dima6120.core_api.navigation.Route

@Composable
fun <R: Route, T> Screen(
    id: String,
    lifecycle: Lifecycle,
    route: R,
    componentHolder: ComponentHolder<R, T>,
    content: @Composable (T) -> Unit
) {
    val applicationComponentProvider = LocalApplicationComponentProvider.current

    val component = remember {
        componentHolder.create(
            id = id,
            lifecycle = lifecycle,
            route = route,
            applicationComponentProvider = applicationComponentProvider
        )
    }

    content(component)
}