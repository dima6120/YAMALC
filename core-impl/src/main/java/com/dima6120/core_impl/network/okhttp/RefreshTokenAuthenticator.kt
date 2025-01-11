package com.dima6120.core_impl.network.okhttp

import com.dima6120.core_api.coroutines.JobSynchronizer
import com.dima6120.core_api.network.repository.LoginRepository
import com.dima6120.core_impl.network.repository.TokensRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class RefreshTokenAuthenticator @Inject constructor(
    private val tokensRepository: Provider<TokensRepository>,
    private val loginRepository: Provider<LoginRepository>
): Authenticator {

    private val synchronizer = JobSynchronizer<String?>()

    override fun authenticate(route: Route?, response: Response): Request? {

        val authToken = runBlocking {
            synchronizer.runOrJoin {
                val refreshToken = tokensRepository.get().getRefreshToken() ?: return@runOrJoin null

                loginRepository.get().refreshAuthToken(refreshToken)

                tokensRepository.get().getAccessToken()
            }
        }

        return authToken?.let {
            response.request
                .newBuilder()
                .header(HttpHeaders.AUTHORIZATION_HEADER, "Bearer $it")
                .build()
        }
    }

    companion object {

        private val TAG = RefreshTokenAuthenticator::class.java.simpleName
    }
}