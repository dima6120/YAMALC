package com.dima6120.core_api.navigation

import javax.inject.Provider

interface ScreensProvider {

    fun provideNavGraphProvidersMap(): Map<Class<out Route>, @JvmSuppressWildcards Provider<ScreenProvider>>
}