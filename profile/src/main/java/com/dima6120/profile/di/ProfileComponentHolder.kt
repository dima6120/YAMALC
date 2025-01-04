package com.dima6120.profile.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder
import com.dima6120.profile_api.ProfileRoute

internal object ProfileComponentHolder: AbstractComponentHolder<ProfileRoute, ProfileComponent>() {

    override val componentName: String = "Profile"

    override fun createComponent(
        route: ProfileRoute,
        applicationComponentProvider: ApplicationComponentProvider
    ): ProfileComponent =
        DaggerProfileComponent.factory().createProfileComponent(applicationComponentProvider)
}