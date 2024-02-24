package com.nexters.ilab.android.feature.home

import com.nexters.ilab.android.core.domain.entity.ProfileEntity
import com.nexters.ilab.android.core.domain.entity.StyleEntity

data class HomeState(
    val isLoading: Boolean = false,
    val styleImageList: List<StyleEntity> = emptyList(),
    val profileImageList: List<ProfileEntity> = emptyList(),
    val isProfileImageDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val selectedProfileEntity: ProfileEntity = ProfileEntity("", "", ""),
    val selectedStyleEntity: StyleEntity = StyleEntity(0, "", ""),
)
