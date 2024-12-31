package com.dima6120.core

import android.content.Context
import com.dima6120.core_api.CoreComponentProvider
import com.dima6120.core_impl.di.DaggerCoreComponent

object CoreComponentProviderFactory {

    fun createCoreComponentProvider(context: Context): CoreComponentProvider =
        DaggerCoreComponent.factory().createCoreComponent(context)
}