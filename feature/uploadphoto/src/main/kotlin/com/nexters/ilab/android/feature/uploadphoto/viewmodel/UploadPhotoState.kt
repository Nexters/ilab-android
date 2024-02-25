package com.nexters.ilab.android.feature.uploadphoto.viewmodel

import com.nexters.ilab.android.core.domain.entity.StyleEntity

data class UploadPhotoState(
    val isLoading: Boolean = false,
    val isPrivacyPolicyAgreed: Boolean = false,
    val selectedPhotoUri: String = "",
    val selectedStyle: String = "",
    val isPermissionDialogVisible: Boolean = false,
    val isUploadPhotoDialogVisible: Boolean = false,
    val styleList: List<StyleEntity> = emptyList(),
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
