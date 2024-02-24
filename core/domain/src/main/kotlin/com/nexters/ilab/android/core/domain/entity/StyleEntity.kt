package com.nexters.ilab.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class StyleEntity(
    val id: Int,
    val name: String,
    val presetStyle: String,
    val defaultImageUrl: String
)
