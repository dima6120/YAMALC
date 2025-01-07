package com.dima6120.core_impl.network.model.anime

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlternativeTitles(

    @SerialName("ja")
    val japanese: String? = null
)
