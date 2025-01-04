package com.dima6120.core_api.storage

import kotlinx.coroutines.flow.Flow

interface AppPreferences {

   suspend fun putString(key: String, value: String)

   suspend fun getString(key: String): String?

   suspend fun removeString(key: String)

   fun getStringFlow(key: String): Flow<String?>
}