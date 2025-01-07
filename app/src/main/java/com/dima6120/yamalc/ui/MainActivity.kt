package com.dima6120.yamalc.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dima6120.core_api.AppWithApplicationComponent
import com.dima6120.core_api.navigation.CustomNavTypes
import com.dima6120.core_api.navigation.Route
import com.dima6120.ui.LocalApplicationComponentProvider
import com.dima6120.core_api.navigation.ScreenProvider
import com.dima6120.core_api.network.repository.LoginRepository
import com.dima6120.splash_api.SplashRoute
import com.dima6120.yamalc.di.MainActivityComponent
import com.dima6120.ui.theme.YAMALCTheme
import de.palm.composestateevents.EventEffect
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var screenProviders: Map<Class<out Route>, @JvmSuppressWildcards Provider<ScreenProvider>>

    // TEST
    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var viewModelFactory: MainActivityViewModel.Factory

    private val viewModel: MainActivityViewModel by viewModels(factoryProducer = { viewModelFactory })

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
                    val state = viewModel.state.value

                    EventEffect(
                        event = state.loginError,
                        onConsumed = viewModel::loginErrorConsumed
                    ) {
                        // TODO
                    }

                    NavHost(
                        navController = navController,
                        startDestination = SplashRoute
                    ) {
                        screenProviders.values.forEach { screenProvider ->
                            screenProvider.get().provideDestination(
                                navGraphBuilder = this,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val code = intent?.data?.let(loginRepository::extractCode)

        code?.let(viewModel::getToken)
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName
    }
}
