package com.dima6120.core_impl.network.service

import com.dima6120.core_impl.network.model.AuthToken
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @POST("token")
    @FormUrlEncoded
    suspend fun getAuthToken(
        @Field("client_id") clientId: String,
        @Field("code") code: String?,
        @Field("code_verifier") codeVerifier: String,
        @Field("grant_type") grantType: String
    ): AuthToken

    @POST("token")
    @FormUrlEncoded
    fun refreshAuthTokenAsync(
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String
    ): AuthToken
}