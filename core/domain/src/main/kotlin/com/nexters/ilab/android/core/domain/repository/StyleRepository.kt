package com.nexters.ilab.android.core.domain.repository

import com.nexters.ilab.android.core.domain.entity.StyleEntity

interface StyleRepository {
    suspend fun getStyleList(): Result<List<StyleEntity>>
}
