package com.dima6120.core_impl.network.okhttp

import android.util.Log
import com.dima6120.core_impl.BuildConfig
import com.dima6120.core_impl.network.repository.TokensRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

class RequestInterceptor @Inject constructor(
    private val tokensRepository: Provider<TokensRepository>
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(HttpHeaders.CLIENT_ID_HEADER, BuildConfig.CLIENT_ID)
            .apply {
                try {
                    runBlocking {
                        tokensRepository.get().getAccessToken()?.let {
                            addHeader(HttpHeaders.AUTHORIZATION_HEADER, "Bearer $it")
                        }
                    }
                } catch (t: Throwable) {
                    Log.w(TAG, t)
                }
            }
            .build()

        return chain.proceed(request)
    }

    companion object {

        private val TAG = RequestInterceptor::class.java.simpleName
    }
}