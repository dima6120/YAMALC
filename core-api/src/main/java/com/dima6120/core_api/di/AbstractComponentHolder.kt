package com.dima6120.core_api.di

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.navigation.Route

abstract class AbstractComponentHolder<R: Route, T>: ComponentHolder<R, T> {
    abstract val componentName: String

    private val components = mutableMapOf<String, T>()

    override fun create(
        id: String,
        lifecycle: Lifecycle,
        route: R,
        applicationComponentProvider: ApplicationComponentProvider
    ): T = components.getOrPut(id) {
        Log.d(TAG, "Creating $componentName:$id component")

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.d(TAG, "$componentName:$id:$event")

                if (event == Lifecycle.Event.ON_DESTROY) {
                    Log.d(TAG, "Destroying $componentName:$id component")

                    components.remove(id)
                    lifecycle.removeObserver(this)
                }
            }
        })

        createComponent(route, applicationComponentProvider)
    }

    abstract fun createComponent(route: R, applicationComponentProvider: ApplicationComponentProvider): T

    companion object {
        private val TAG = AbstractComponentHolder::class.java.simpleName
    }
}