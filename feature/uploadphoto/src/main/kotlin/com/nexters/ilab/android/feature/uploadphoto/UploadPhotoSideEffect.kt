package com.nexters.ilab.android.feature.uploadphoto

sealed interface UploadPhotoSideEffect {
    data object openPhotoPicker : UploadPhotoSideEffect
    data object openCamera : UploadPhotoSideEffect
    data object UploadPhotoSuccess : UploadPhotoSideEffect
}
