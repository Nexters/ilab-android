package com.nexters.ilab.android.feature.uploadphoto

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class UploadPhotoState(
    val isLoading: Boolean = false,
    val selectedPhotoUri: String = "",
    val selectedStyle: String = "",
    val createdImageList: ImmutableList<String> = persistentListOf(),
    val isPermissionDialogVisible: Boolean = false,
    val isUploadPhotoDialogVisible: Boolean = false,
    val isCreateImageStopDialogVisible: Boolean = false,
)
