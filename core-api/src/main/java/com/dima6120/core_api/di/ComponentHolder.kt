package com.dima6120.core_api.di

import androidx.lifecycle.Lifecycle
import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.navigation.Route

interface ComponentHolder<R: Route, T> {

    fun create(
        id: String,
        lifecycle: Lifecycle,
        route: R,
        applicationComponentProvider: ApplicationComponentProvider
    ): T
}