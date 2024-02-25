package com.nexters.ilab.android.core.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("signInType")
    val signInType: String = "KAKAO",
)
