package com.nexters.ilab.feature.createimage.viewmodel

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CreateImageState(
    val isLoading: Boolean = false,
    val createdProfileImageList: ImmutableList<String> = persistentListOf(),
    val isUploadPhotoDialogVisible: Boolean = false,
    val isCreateImageStopDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
