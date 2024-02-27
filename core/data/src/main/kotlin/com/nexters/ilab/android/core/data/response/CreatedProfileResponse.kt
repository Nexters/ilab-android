package com.nexters.ilab.android.core.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatedProfileResponse(
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val url: String,
)
