package com.nexters.ilab.android.core.data.mapper

import com.nexters.ilab.android.core.data.response.StyleResponse
import com.nexters.ilab.android.core.domain.entity.StyleEntity

internal fun StyleResponse.toEntity() =
    StyleEntity(
        id = id,
        name = name,
        defaultImageUrl = defaultImageUrl,
    )
