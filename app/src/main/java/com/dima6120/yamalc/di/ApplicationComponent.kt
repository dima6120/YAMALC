package com.dima6120.yamalc.di

import android.content.Context
import com.dima6120.anime_lists.di.AnimeListsNavGraphProviderExternalModule
import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.CoreComponentProvider
import com.dima6120.main.di.MainNavGraphProviderExternalModule
import com.dima6120.profile.di.ProfileNavGraphProviderExternalModule
import com.dima6120.search.di.SearchNavGraphProviderExternalModule
import com.dima6120.splash.di.SplashNavGraphProviderExternalModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [CoreComponentProvider::class],
    modules = [
        SplashNavGraphProviderExternalModule::class,
        MainNavGraphProviderExternalModule::class,
        ProfileNavGraphProviderExternalModule::class,
        SearchNavGraphProviderExternalModule::class,
        AnimeListsNavGraphProviderExternalModule::class
    ]
)
interface ApplicationComponent: ApplicationComponentProvider {

    @Component.Factory
    interface Factory {

        fun createApplicationComponent(
            @BindsInstance context: Context,
            coreComponentProvider: CoreComponentProvider
        ): ApplicationComponent
    }
}