package com.dima6120.yamalc

import android.app.Application
import com.dima6120.core_api.AppWithApplicationComponent
import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.yamalc.di.ApplicationComponent

class App: Application(), AppWithApplicationComponent {

    private var applicationComponent: ApplicationComponent? = null

    override fun getApplicationComponentProvider(): ApplicationComponentProvider =
        applicationComponent
            ?: DaggerApplicationComponent.factory()
                .createApplicationComponent(this).also { applicationComponent = it }
}