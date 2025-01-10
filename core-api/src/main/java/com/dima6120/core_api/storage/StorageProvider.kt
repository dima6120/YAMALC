package com.dima6120.core_api.storage

interface StorageProvider {

    fun providePreferencesRepository(): AppPreferences
}