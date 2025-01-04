package com.dima6120.core_impl.network.repository

import android.net.Uri
import com.dima6120.core_impl.BuildConfig
import com.dima6120.core_impl.network.model.AuthToken
import com.dima6120.core_api.storage.AppPreferences
import com.dima6120.core_impl.error.InternalErrorHandler
import com.dima6120.core_impl.network.service.AuthService
import com.dima6120.core_impl.security.manager.CryptoManager
import com.dima6120.core_impl.utils.PkceGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val oauthService: AuthService,
    private val cryptoManager: CryptoManager,
    private val appPreferences: AppPreferences,
    private val internalErrorHandler: InternalErrorHandler
): AuthRepository {

    private val json = Json.Default
    private val codeVerifier = PkceGenerator.generateVerifier(128)

    override fun getLoggedInFlow(): Flow<Boolean> = appPreferences.getStringFlow(AUTH_TOKEN_KEY).map { it != null }

    override suspend fun getAccessToken(): String? = getAuthToken()?.accessToken

    override suspend fun getRefreshToken(): String? = getAuthToken()?.refreshToken

    override fun getOAuth2AuthorizeURL(): String =
        "${BuildConfig.OAUTH_ENDPONT}authorize?response_type=code&client_id=${BuildConfig.CLIENT_ID}&code_challenge=${codeVerifier}&state=$STATE"

    override fun extractCode(uri: Uri): String? {
        val code = uri.getQueryParameter(CODE_PARAMETER)
        val state = uri.getQueryParameter(STATE_PARAMETER)

        return code.takeIf { state == STATE }
    }

    override suspend fun getAuthToken(code: String) =
        internalErrorHandler.run {
            val authToken = oauthService.getAuthToken(
                clientId = BuildConfig.CLIENT_ID,
                code = code,
                codeVerifier = codeVerifier,
                grantType = GET_GRANT_TYPE
            )

            putAuthToken(authToken)
        }

    override suspend fun refreshAuthToken(refreshToken: String) =
        internalErrorHandler.run {
            val authToken = oauthService.refreshAuthTokenAsync(
                clientId = BuildConfig.CLIENT_ID,
                refreshToken = refreshToken,
                grantType = REFRESH_GRANT_TYPE
            )

            putAuthToken(authToken)
        }

    override suspend fun deleteAuthToken() {
        appPreferences.removeString(AUTH_TOKEN_KEY)
    }

    private suspend fun putAuthToken(authToken: AuthToken) {
        appPreferences.putString(AUTH_TOKEN_KEY, encrypt(authToken))
    }

    private suspend fun getAuthToken(): AuthToken? = appPreferences.getString(AUTH_TOKEN_KEY)?.let(::decrypt)

    private fun encrypt(authToken: AuthToken): String {
        val jsonString = json.encodeToString(AuthToken.serializer(), authToken)

        return cryptoManager.encrypt(jsonString)
    }

    private fun decrypt(encrypted: String): AuthToken {
        val jsonString = cryptoManager.decrypt(encrypted)

        return json.decodeFromString(jsonString)
    }

    companion object {

        private const val AUTH_TOKEN_KEY = "auth_token"

        private const val CODE_PARAMETER = "code"
        private const val STATE_PARAMETER = "state"

        private const val STATE = "YAMALC"
        private const val GET_GRANT_TYPE = "authorization_code"
        private const val REFRESH_GRANT_TYPE = "refresh_token"
    }
}