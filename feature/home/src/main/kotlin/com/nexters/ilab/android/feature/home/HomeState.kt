package com.nexters.ilab.android.feature.home

data class ProfileImage(
    val profileImage: String = "",
    val profileKeyword: String = "",
)
data class HomeState(
    val styleImageList: List<ProfileImage> = emptyList(),
    val profileImageList: List<ProfileImage> = emptyList(),
)
