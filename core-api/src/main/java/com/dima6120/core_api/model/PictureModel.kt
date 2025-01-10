package com.dima6120.core_api.model

import kotlinx.serialization.Serializable

@Serializable
data class PictureModel(
    val medium: String,
    val large: String?,
)
