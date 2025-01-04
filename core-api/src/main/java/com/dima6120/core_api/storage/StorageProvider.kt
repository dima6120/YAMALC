package com.dima6120.core_api.storage

import com.dima6120.core_api.storage.AppPreferences

interface StorageProvider {

    fun providePreferencesRepository(): AppPreferences
}