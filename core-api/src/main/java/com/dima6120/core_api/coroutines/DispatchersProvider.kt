package com.dima6120.core_api.coroutines

import com.dima6120.core_api.di.DispatcherDefault
import com.dima6120.core_api.di.DispatcherIO
import com.dima6120.core_api.di.DispatcherMain
import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {

    @DispatcherMain
    fun provideMainDispatcher(): CoroutineDispatcher

    @DispatcherIO
    fun provideIODispatcher(): CoroutineDispatcher

    @DispatcherDefault
    fun provideDefaultDispatcher(): CoroutineDispatcher
}