package com.dima6120.core_impl.di

import com.dima6120.core_api.di.DispatcherDefault
import com.dima6120.core_api.di.DispatcherIO
import com.dima6120.core_api.di.DispatcherMain
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
interface DispatchersModule {

    companion object {

        @Provides
        @DispatcherMain
        fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

        @Provides
        @DispatcherIO
        fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        @DispatcherDefault
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
    }
}