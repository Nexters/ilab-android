package com.nexters.ilab.android.core.data.mapper

import com.nexters.ilab.android.core.data.response.StyleResponse
import com.nexters.ilab.android.core.data.response.UserInfoResponse
import com.nexters.ilab.android.core.domain.entity.StyleEntity
import com.nexters.ilab.android.core.domain.entity.UserInfoEntity

internal fun StyleResponse.toEntity() =
    StyleEntity(
        id = id,
        name = name,
        defaultImageUrl = defaultImageUrl,
    )

internal fun UserInfoResponse.toEntity() =
    UserInfoEntity(
        id = id,
        uuid = uuid,
        email = email,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
    )
