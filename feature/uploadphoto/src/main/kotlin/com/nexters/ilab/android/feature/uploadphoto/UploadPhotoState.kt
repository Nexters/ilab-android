package com.nexters.ilab.android.feature.uploadphoto

data class UploadPhotoState(
    val selectedPhotoUri: String = "",
    val selectedKeyword: String = "",
    val createdImageList: List<Pair<String, String>> = emptyList(),
    val isPermissionDialogVisible: Boolean = false,
    val isUploadPhotoDialogVisible: Boolean = false,
)
