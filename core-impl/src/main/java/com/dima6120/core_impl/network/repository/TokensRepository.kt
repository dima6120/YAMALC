package com.dima6120.core_impl.network.repository

interface TokensRepository {

    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?
}