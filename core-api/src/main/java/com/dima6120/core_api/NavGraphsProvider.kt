package com.dima6120.core_api

import com.dima6120.core_api.navigation.NavGraphProvider

interface NavGraphsProvider {

    fun provideNavGraphProvidersSet(): Set<NavGraphProvider>
}