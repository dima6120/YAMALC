package com.dima6120.profile.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder

internal object ProfileComponentHolder: AbstractComponentHolder<ProfileComponent>() {

    override val componentName: String = "Profile"

    override fun createComponent(applicationComponentProvider: ApplicationComponentProvider): ProfileComponent =
        DaggerProfileComponent.factory().createProfileComponent(applicationComponentProvider)
}