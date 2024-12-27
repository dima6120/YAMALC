package com.dima6120.yamalc.di

import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.yamalc.DaggerMainActivityComponent
import com.dima6120.yamalc.MainActivity
import dagger.Component


@Component(
    dependencies = [ApplicationComponentProvider::class]
)
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)

    companion object {

        fun create(applicationComponentProvider: ApplicationComponentProvider): MainActivityComponent =
            DaggerMainActivityComponent.builder()
                .applicationComponentProvider(applicationComponentProvider)
                .build()
    }
}