package com.dima6120.core_impl.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(

    @SerialName("expires_in")
    val expiresIn: Int,

    @SerialName("access_token")
    val accessToken: String,

    @SerialName("token_type")
    val tokenType: String,

    @SerialName("refresh_token")
    val refreshToken: String
)
