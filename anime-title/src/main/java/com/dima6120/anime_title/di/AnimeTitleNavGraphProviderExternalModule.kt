package com.dima6120.anime_title.di

import com.dima6120.anime_title.AnimeTitleScreenProviderImpl
import com.dima6120.anime_title_api.AnimeTitleRoute
import com.dima6120.core_api.di.RouteKey
import com.dima6120.core_api.navigation.ScreenProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface AnimeTitleNavGraphProviderExternalModule {

    @Binds
    @IntoMap
    @RouteKey(AnimeTitleRoute::class)
    fun provideAnimeTitleNavGraphProvider(impl: AnimeTitleScreenProviderImpl): ScreenProvider
}