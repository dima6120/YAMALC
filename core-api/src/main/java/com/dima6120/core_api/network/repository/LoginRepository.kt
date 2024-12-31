package com.dima6120.core_api.network.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun getLoggedInFlow(): Flow<Boolean>

    fun getOAuth2AuthorizeURL(): String

    fun extractCode(uri: Uri): String?

    suspend fun getAuthToken(code: String)

    suspend fun refreshAuthToken(refreshToken: String)

    suspend fun deleteAuthToken()
}