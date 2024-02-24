package com.nexters.ilab.android.core.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StyleResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("presetStyle")
    val presetStyle: String,
    @SerialName("defaultImageUrl")
    val defaultImageUrl: String,
)
