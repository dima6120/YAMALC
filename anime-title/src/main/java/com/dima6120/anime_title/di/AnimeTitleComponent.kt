package com.dima6120.anime_title.di

import com.dima6120.anime_title.ui.AnimeTitleViewModel
import com.dima6120.core_api.ApplicationComponentProvider
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [ApplicationComponentProvider::class],
    modules = [UseCaseModule::class]
)
interface AnimeTitleComponent {

    fun provideAnimeTitleViewModelFactory(): AnimeTitleViewModel.Factory

    @Component.Factory
    interface Factory {

        fun createAnimeTitleComponent(
            @BindsInstance animeId: Int,
            applicationComponentProvider: ApplicationComponentProvider
        ): AnimeTitleComponent
    }
}