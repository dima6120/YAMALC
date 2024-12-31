package com.dima6120.core_impl.storage.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dima6120.core_api.storage.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppPreferencesImpl @Inject constructor(private val context: Context): AppPreferences {

    private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

    private inline val data: Flow<Preferences>
        get() = context.dataStore.data

    override suspend fun putString(key: String, value: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun getString(key: String): String? = data.firstOrNull()?.get(stringPreferencesKey(key))

    override suspend fun removeString(key: String) {
        context.dataStore.edit {
            it.remove(stringPreferencesKey(key))
        }
    }

    override fun getStringFlow(key: String): Flow<String?> = data.map { it[stringPreferencesKey(key)] }


    companion object {

        private const val PREFERENCES_NAME = "yamalc_preferences"
    }
}