package com.nexters.ilab.android.core.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("id")
    val id: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("name")
    val name: String,
)
