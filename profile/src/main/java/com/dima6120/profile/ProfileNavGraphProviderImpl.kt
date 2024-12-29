package com.dima6120.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dima6120.profile.ui.ProfileScreen
import com.dima6120.profile_api.ProfileNavGraphProvider
import com.dima6120.profile_api.ProfileRoute
import javax.inject.Inject

class ProfileNavGraphProviderImpl @Inject constructor(): ProfileNavGraphProvider {

    override fun provideNavGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<ProfileRoute> {
            ProfileScreen(
                navBackStackEntry = it,
                navController = navController
            )
        }
    }
}