package com.dima6120.core_api.navigation

import javax.inject.Provider

interface NavGraphsProvider {

    fun provideNavGraphProvidersMap(): Map<Class<*>, @JvmSuppressWildcards Provider<NavGraphProvider>>
}