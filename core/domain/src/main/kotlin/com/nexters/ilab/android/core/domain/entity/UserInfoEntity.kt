package com.nexters.ilab.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class UserAlbumImageStyleEntity(
    val id: Int = 0,
    val name: String = "",
)

@Stable
data class UserAlbumImageEntity(
    val id: Int = 0,
    val imageUrl: String = "",
    val imageStyle: UserAlbumImageStyleEntity = UserAlbumImageStyleEntity(0, ""),
)

@Stable
data class UserThumbnailEntity(
    val id: Int = 0,
    val images: List<UserAlbumImageEntity> = emptyList(),
)

@Stable
data class UserInfoEntity(
    val id: Long = 0L,
    val uuid: Long = 0L,
    val email: String = "",
    val nickname: String = "",
    val profileImageUrl: String = "",
    val thumbnails: List<UserThumbnailEntity> = emptyList(),
)
