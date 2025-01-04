package com.dima6120.anime_title.di

import com.dima6120.anime_title_api.AnimeTitleRoute
import com.dima6120.core_api.ApplicationComponentProvider
import com.dima6120.core_api.di.AbstractComponentHolder

object AnimeTitleComponentHolder: AbstractComponentHolder<AnimeTitleRoute, AnimeTitleComponent>() {

    override val componentName: String = "AnimeTitle"

    override fun createComponent(
        route: AnimeTitleRoute,
        applicationComponentProvider: ApplicationComponentProvider
    ): AnimeTitleComponent =
        DaggerAnimeTitleComponent.factory().createAnimeTitleComponent(
            animeId = route.animeId.id,
            applicationComponentProvider = applicationComponentProvider
        )

}