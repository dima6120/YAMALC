package com.dima6120.core_impl.di

import com.dima6120.core_api.storage.AppPreferences
import com.dima6120.core_impl.storage.repository.AppPreferencesImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface StorageModule {

    @Singleton
    @Binds
    fun provideAppPreferences(impl: AppPreferencesImpl): AppPreferences
}