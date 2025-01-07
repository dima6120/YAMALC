package com.dima6120.core_impl.network.model

import com.dima6120.core_api.model.StudioModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Studio(

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)

fun Studio.toStudioModel(): StudioModel =
    StudioModel(
        id = this.id,
        name = this.name
    )
