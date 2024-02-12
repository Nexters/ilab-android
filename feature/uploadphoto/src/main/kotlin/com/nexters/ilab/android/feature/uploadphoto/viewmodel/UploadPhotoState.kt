package com.nexters.ilab.android.feature.uploadphoto.viewmodel

data class UploadPhotoState(
    val isPrivacyPolicyAgreed: Boolean = false,
    val selectedPhotoUri: String = "",
    val selectedStyle: String = "",
    val isPermissionDialogVisible: Boolean = false,
    val isUploadPhotoDialogVisible: Boolean = false,
)
