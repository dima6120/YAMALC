package com.dima6120.core_impl.network.model

import com.dima6120.core_api.model.PictureModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Picture(

    @SerialName("large")
    val large: String? = null,

    @SerialName("medium")
    val medium: String
)

fun Picture.toPictureModel(): PictureModel =
    PictureModel(
        large = this.large,
        medium = this.medium
    )
