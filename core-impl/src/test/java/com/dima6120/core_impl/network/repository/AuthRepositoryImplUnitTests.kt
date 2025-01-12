package com.dima6120.core_impl.network.repository

import android.net.Uri
import com.dima6120.core_api.storage.AppPreferences
import com.dima6120.core_impl.error.InternalErrorHandler
import com.dima6120.core_impl.network.model.AuthToken
import com.dima6120.core_impl.network.repository.AuthRepositoryImpl
import com.dima6120.core_impl.network.service.AuthService
import com.dima6120.core_impl.security.manager.CryptoManager
import com.dima6120.core_impl.utils.AppPreferencesKey
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Test

class AuthRepositoryImplUnitTests {

    private val oauthService = mockk<AuthService>(relaxed = true)
    private val cryptoManager = mockk<CryptoManager>(relaxed = true)
    private val appPreferences = mockk<AppPreferences>(relaxed = true)
    private val internalErrorHandler = mockk<InternalErrorHandler>(relaxed = true)

    private val refreshToken = "refreshToken"
    private val accessToken = "accessToken"

    private val authToken = AuthToken(
        expiresIn = 0,
        accessToken = accessToken,
        tokenType = "Bearer",
        refreshToken = refreshToken
    )

    private val decryptedToken = Json.encodeToString(AuthToken.serializer(), authToken)
    private val enryptedToken = "encrypted"

    private val code = "code"
    private val rightState = "YAMALC"
    private val wrongState = "Wrong"

    private val authRepositoryImpl = AuthRepositoryImpl(
        oauthService = oauthService,
        cryptoManager = cryptoManager,
        appPreferences = appPreferences,
        internalErrorHandler = internalErrorHandler
    )

    @Test
    fun getAccessToken() =
        runTest {
            // given
            val decryptedToken = Json.encodeToString(AuthToken.serializer(), authToken)
            val enryptedToken = "encrypted"

            coEvery { appPreferences.getString(AppPreferencesKey.AUTH_TOKEN_KEY) } returns enryptedToken
            every { cryptoManager.decrypt(enryptedToken) } returns decryptedToken

            // when
            val givenAccessToken = authRepositoryImpl.getAccessToken()

            // then
            verify { cryptoManager.decrypt(enryptedToken) }
            coVerify { appPreferences.getString(AppPreferencesKey.AUTH_TOKEN_KEY) }

            assert(givenAccessToken == accessToken)
        }

    @Test
    fun getRefreshToken() =
        runTest {
            // given
            coEvery { appPreferences.getString(AppPreferencesKey.AUTH_TOKEN_KEY) } returns enryptedToken
            every { cryptoManager.decrypt(enryptedToken) } returns decryptedToken

            // when
            val givenRefreshToken = authRepositoryImpl.getRefreshToken()

            // then
            verify { cryptoManager.decrypt(enryptedToken) }
            coVerify { appPreferences.getString(AppPreferencesKey.AUTH_TOKEN_KEY) }

            assert(givenRefreshToken == refreshToken)
        }

    @Test
    fun extractCodeWithRightState() {
        // given
        val uri = mockk<Uri>(relaxed = true)

        every { uri.getQueryParameter("code") } returns code
        every { uri.getQueryParameter("state") } returns rightState

        // when
        val extractedCode = authRepositoryImpl.extractCode(uri)

        // then
        assert(extractedCode == code)
    }

    @Test
    fun extractCodeWithWrongState() {
        // given
        val uri = mockk<Uri>(relaxed = true)

        every { uri.getQueryParameter("code") } returns code
        every { uri.getQueryParameter("state") } returns wrongState

        // when
        val extractedCode = authRepositoryImpl.extractCode(uri)

        // then
        assert(extractedCode == null)
    }

    @Test
    fun getAuthToken() =
        runTest {
            // given
            coEvery { oauthService.getAuthToken(any(), code, any(), any()) } returns authToken
            coEvery { internalErrorHandler.run(captureLambda<suspend () -> Unit>()) } coAnswers {
                lambda<suspend () -> Unit>().captured.invoke()
            }
            every { cryptoManager.encrypt(decryptedToken) } returns enryptedToken
            coEvery { appPreferences.putString(AppPreferencesKey.AUTH_TOKEN_KEY, enryptedToken) } just Runs

            // when
            authRepositoryImpl.getAuthToken(code)

            // then
            verify { cryptoManager.encrypt(decryptedToken) }
            coVerify { oauthService.getAuthToken(any(), code, any(), any()) }
            coVerify { internalErrorHandler.run(captureLambda<suspend () -> Unit>()) }
            coVerify { appPreferences.putString(AppPreferencesKey.AUTH_TOKEN_KEY, enryptedToken) }
        }

    @Test
    fun refreshAuthToken() =
        runTest {
            // given
            coEvery { oauthService.refreshAuthTokenAsync(any(), refreshToken, any()) } returns authToken
            coEvery { internalErrorHandler.run(captureLambda<suspend () -> Unit>()) } coAnswers {
                lambda<suspend () -> Unit>().captured.invoke()
            }
            every { cryptoManager.encrypt(decryptedToken) } returns enryptedToken
            coEvery { appPreferences.putString(AppPreferencesKey.AUTH_TOKEN_KEY, enryptedToken) } just Runs

            // when
            authRepositoryImpl.refreshAuthToken(refreshToken)

            // then
            verify { cryptoManager.encrypt(decryptedToken) }
            coVerify { oauthService.refreshAuthTokenAsync(any(), refreshToken, any()) }
            coVerify { internalErrorHandler.run(captureLambda<suspend () -> Unit>()) }
            coVerify { appPreferences.putString(AppPreferencesKey.AUTH_TOKEN_KEY, enryptedToken) }
        }

    @Test
    fun deleteAuthToken() =
        runTest {
            // given
            coEvery { appPreferences.removeString(AppPreferencesKey.AUTH_TOKEN_KEY) } just Runs

            // when
            authRepositoryImpl.deleteAuthToken()

            // then
            coVerify { appPreferences.removeString(AppPreferencesKey.AUTH_TOKEN_KEY) }
        }
}