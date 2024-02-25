package com.nexters.ilab.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class UserInfoEntity(
    val id: Long = 0L,
    val uuid: Long = 0L,
    val email: String = "",
    val nickname: String = "",
    val profileImageUrl: String = "",
)
