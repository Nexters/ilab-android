package com.nexters.ilab.android.core.data.mapper

import com.nexters.ilab.android.core.data.response.CreatedProfileResponse
import com.nexters.ilab.android.core.data.response.ProfileResponse
import com.nexters.ilab.android.core.data.response.StyleResponse
import com.nexters.ilab.android.core.data.response.UserInfoResponse
import com.nexters.ilab.android.core.domain.entity.CreatedProfileEntity
import com.nexters.ilab.android.core.domain.entity.ProfileEntity
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

internal fun ProfileResponse.toEntity() =
    ProfileEntity(
        id = id,
        imageUrl = imageUrl,
        name = name,
    )

internal fun CreatedProfileResponse.toEntity() =
    CreatedProfileEntity(
        id = id,
        url = url,
    )
