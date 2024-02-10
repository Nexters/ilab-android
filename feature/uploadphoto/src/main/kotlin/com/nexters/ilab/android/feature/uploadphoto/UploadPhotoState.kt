package com.nexters.ilab.android.feature.uploadphoto

data class UploadPhotoState(
    val isLoading: Boolean = false,
    val selectedPhotoUri: String = "",
    val selectedStyle: String = "",
    val createdImageList: List<Pair<String, String>> = emptyList(),
    val isPermissionDialogVisible: Boolean = false,
    val isUploadPhotoDialogVisible: Boolean = false,
    val isCreateImageStopDialogVisible: Boolean = false,
)
