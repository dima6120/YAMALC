package com.dima6120.core_api.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface ScreenProvider {

    fun provideDestination(
        lifecycle: Lifecycle? = null,
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    )
}