package com.nexters.ilab.feature.createimage.viewmodel

import com.nexters.ilab.android.core.common.UiText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CreateImageState(
    val isLoading: Boolean = false,
    val creatingImageWaitText: UiText = UiText.DirectString(""),
    val createdProfileImageList: ImmutableList<String> = persistentListOf(),
    val isUploadPhotoDialogVisible: Boolean = false,
    val isCreateImageStopDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
