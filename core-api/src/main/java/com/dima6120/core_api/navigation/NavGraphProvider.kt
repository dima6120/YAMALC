package com.dima6120.core_api.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface NavGraphProvider {

    fun provideNavGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    )
}