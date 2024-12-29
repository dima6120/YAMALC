package com.dima6120.core_api

import com.dima6120.core_api.navigation.NavGraphProvider
import javax.inject.Provider

interface NavGraphsProvider {

    fun provideNavGraphProvidersMap(): Map<Class<*>, @JvmSuppressWildcards Provider<NavGraphProvider>>
}