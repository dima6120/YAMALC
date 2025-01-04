package com.dima6120.core_impl.network.okhttp

import android.util.Log
import com.dima6120.core_api.network.repository.LoginRepository
import com.dima6120.core_impl.network.repository.TokensRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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

    private val synchronizer = RefreshTokenJobSynchronizer()

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

    private class RefreshTokenJobSynchronizer {

        private var activeJob: Deferred<String?>? = null
        private val mutex = Mutex()

        suspend fun runOrJoin(block: suspend () -> String?): String? =
            coroutineScope {

                val deferredJob = mutex.withLock(owner = this@coroutineScope) {

                    var activeJob: Deferred<String?>? = activeJob

                    if (activeJob == null || activeJob.isCancelled) {

                        Log.i(TAG, "Running new refresh token job")

                        activeJob = async {

                            block().also {
                                mutex.withLock(owner = this@coroutineScope) {
                                    this@RefreshTokenJobSynchronizer.activeJob = null
                                }
                            }
                        }

                        this@RefreshTokenJobSynchronizer.activeJob = activeJob
                    }

                    activeJob
                }

                Log.i(TAG, "Joining to existing refresh token job")

                deferredJob.await()
            }
    }

    companion object {

        private val TAG = RefreshTokenAuthenticator::class.java.simpleName
    }
}