package com.nexters.ilab.android.core.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    @SerialName("uuid")
    val uuid: Long,
    @SerialName("signInTypq")
    val signInType: String = "KAKAO",
)
