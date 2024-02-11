package com.nexters.ilab.android.feature.uploadphoto

sealed interface UploadPhotoSideEffect {
    data object OpenPhotoPicker : UploadPhotoSideEffect
    data object RequestCameraPermission : UploadPhotoSideEffect
    data object StartCamera : UploadPhotoSideEffect
    data object UploadPhotoSuccess : UploadPhotoSideEffect
}
