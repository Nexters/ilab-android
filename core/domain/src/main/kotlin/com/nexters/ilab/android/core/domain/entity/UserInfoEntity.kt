package com.nexters.ilab.android.core.domain.entity

import androidx.compose.runtime.Stable

data class UserAlbumImageStyle(
    val id: Int = 0,
    val name: String = "",
)

data class UserAlbumImage(
    val id: Int = 0,
    val imageUrl: String = "",
    val imageStyle: UserAlbumImageStyle = UserAlbumImageStyle(0, ""),
)

data class UserThumbnail(
    val id: Int = 0,
    val images: List<UserAlbumImage> = emptyList(),
)

@Stable
data class UserInfoEntity(
    val id: Long = 0L,
    val uuid: Long = 0L,
    val email: String = "",
    val nickname: String = "",
    val profileImageUrl: String = "",
    val thumbnails: List<UserThumbnail> = emptyList(),
)
