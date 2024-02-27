package com.nexters.ilab.android.core.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("uuid")
    val uuid: Long,
    @SerialName("email")
    val email: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("thumbnails")
    val thumbnails: List<UserThumbnailResponse>?,
)

@Serializable
data class UserThumbnailResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("images")
    val images: List<UserAlbumImageResponse>,
)

@Serializable
data class UserAlbumImageResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("style")
    val imageStyle: UserAlbumImageStyleResponse,
)

@Serializable
data class UserAlbumImageStyleResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
)
