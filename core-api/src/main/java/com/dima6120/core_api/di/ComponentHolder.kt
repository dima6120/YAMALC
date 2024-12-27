package com.dima6120.core_api.di

import androidx.lifecycle.Lifecycle
import com.dima6120.core_api.ApplicationComponentProvider

interface ComponentHolder<T> {

    fun create(
        id: String,
        lifecycle: Lifecycle,
        applicationComponentProvider: ApplicationComponentProvider
    ): T
}