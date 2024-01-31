package com.nexters.ilab.android.feature.uploadphoto

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class UploadPhotoViewModel @Inject constructor() : ViewModel(), ContainerHost<UploadPhotoState, UploadPhotoSideEffect> {

    override val container = container<UploadPhotoState, UploadPhotoSideEffect>(UploadPhotoState())

    fun onPhotoPickerClick() = intent {
        postSideEffect(UploadPhotoSideEffect.openPhotoPicker)
    }

    fun onCameraClick() = intent {
        postSideEffect(UploadPhotoSideEffect.openCamera)
    }

    fun setSelectImageUri(uri: String) = intent {
        reduce {
            state.copy(
                selectedPhotoUri = uri,
            )
        }
        postSideEffect(UploadPhotoSideEffect.UploadPhotoSuccess)
    }
}

