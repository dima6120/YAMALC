package com.dima6120.core_impl.di

import android.content.Context
import com.dima6120.core_api.CoreComponentProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        StorageModule::class,
        SecurityModule::class,
        UtilsModule::class,
        DispatchersModule::class,
        UseCaseModule::class
    ]
)
interface CoreComponent: CoreComponentProvider {

    @Component.Factory
    interface Factory {

        fun createCoreComponent(@BindsInstance context: Context): CoreComponent
    }
}