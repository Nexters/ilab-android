package com.nexters.ilab.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class ProfileEntity(
    val id: String,
    val imageUrl: String,
    val name: String,
)
