package com.dima6120.yamalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dima6120.core_api.AppWithApplicationComponent
import com.dima6120.core_api.compose.LocalApplicationComponentProvider
import com.dima6120.core_api.navigation.NavGraphProvider
import com.dima6120.splash_api.SplashRoute
import com.dima6120.yamalc.di.MainActivityComponent
import com.dima6120.ui.theme.YAMALCTheme
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navGraphProviders: Map<Class<*>, @JvmSuppressWildcards Provider<NavGraphProvider>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val applicationComponentProvider = (application as AppWithApplicationComponent)
            .getApplicationComponentProvider()

        MainActivityComponent
            .create(applicationComponentProvider)
            .inject(this)

//        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalApplicationComponentProvider provides applicationComponentProvider) {
                YAMALCTheme {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = SplashRoute) {
                        navGraphProviders.values.forEach {
                            it.get().provideNavGraph(
                                navGraphBuilder = this,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
