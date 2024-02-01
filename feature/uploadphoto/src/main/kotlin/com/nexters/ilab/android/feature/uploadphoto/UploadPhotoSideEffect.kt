package com.nexters.ilab.android.feature.uploadphoto

sealed interface UploadPhotoSideEffect {
    data object openPhotoPicker : UploadPhotoSideEffect
    data object requestCameraPermission : UploadPhotoSideEffect
    data object startCamera : UploadPhotoSideEffect
    data object UploadPhotoSuccess : UploadPhotoSideEffect
}
