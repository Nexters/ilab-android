package com.nexters.ilab.android.feature.home

data class ProfileImage(
    val profileImage: String = "",
    val profileKeyword: String = "",
)
data class HomeState(
    val isLoading: Boolean = false,
    val styleImageList: List<ProfileImage> = emptyList(),
    val profileImageList: List<ProfileImage> = emptyList(),
    val isProfileImageDialogVisible: Boolean = false,
    val selectedIndex: Int = 0,
)
