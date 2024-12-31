package com.dima6120.core_impl.di

import com.dima6120.core_impl.BuildConfig
import com.dima6120.core_impl.network.okhttp.RefreshTokenAuthenticator
import com.dima6120.core_impl.network.okhttp.RequestInterceptor
import com.dima6120.core_impl.network.service.ApiService
import com.dima6120.core_impl.network.service.AuthService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
interface NetworkModule {

    @Singleton
    @Binds
    @IntoSet
    fun provideRequestInterceptor(impl: RequestInterceptor): Interceptor

    @Singleton
    @Binds
    fun provideAccessTokenAuthenticator(impl: RefreshTokenAuthenticator): Authenticator

    companion object {

        @Singleton
        @Provides
        fun provideConverterFactory(): Converter.Factory =
            Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json; charset=UTF8".toMediaType())

        @Singleton
        @Provides
        @IntoSet
        fun provideLoggingInterceptor(): Interceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        @Singleton
        @Provides
        fun provideOkHttpClient(
            interceptors: Set<@JvmSuppressWildcards Interceptor>,
            authenticator: Authenticator
        ): OkHttpClient =
            OkHttpClient.Builder()
                .apply { interceptors.forEach(::addInterceptor) }
                .authenticator(authenticator)
                .build()

        @Singleton
        @Provides
        @RetrofitOAuth
        fun provideOAuthRetrofit(
            converterFactory: Converter.Factory,
            okHttpClient: OkHttpClient
        ): Retrofit = buildRetrofit(BuildConfig.OAUTH_ENDPONT, converterFactory, okHttpClient)

        @Singleton
        @Provides
        @RetrofitApi
        fun provideApiRetrofit(
            converterFactory: Converter.Factory,
            okHttpClient: OkHttpClient
        ): Retrofit = buildRetrofit(BuildConfig.API_ENDPONT, converterFactory, okHttpClient)

        @Singleton
        @Provides
        fun provideAuthService(@RetrofitOAuth retrofit: Retrofit): AuthService =
            retrofit.create(AuthService::class.java)

        @Singleton
        @Provides
        fun provideApiService(@RetrofitApi retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)

        private fun buildRetrofit(
            endpoint: String,
            converterFactory: Converter.Factory,
            okHttpClient: OkHttpClient
        ): Retrofit =
            Retrofit.Builder()
                .baseUrl(endpoint)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }
}