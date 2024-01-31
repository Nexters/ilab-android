package com.nexters.ilab.android.feature.camera

sealed interface UploadPhotoSideEffect {
    data object openPhotoPicker : UploadPhotoSideEffect
    data object openCamera : UploadPhotoSideEffect
    data object UploadPhotoSuccess : UploadPhotoSideEffect
}
