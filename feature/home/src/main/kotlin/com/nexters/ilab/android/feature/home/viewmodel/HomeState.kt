package com.nexters.ilab.android.feature.home.viewmodel

import com.nexters.ilab.android.core.domain.entity.ProfileEntity
import com.nexters.ilab.android.core.domain.entity.StyleEntity

data class HomeState(
    val isLoading: Boolean = false,
    val styleImageList: List<StyleEntity> = emptyList(),
    val profileImageList: List<ProfileEntity> = emptyList(),
    val isProfileImageDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val selectedProfileEntity: ProfileEntity = ProfileEntity("", "", ""),
    val selectedStyle: StyleEntity = StyleEntity(0, "", ""),
)
