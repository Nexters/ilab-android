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

    init {
        intent {
            reduce {
                state.copy(createdImageList = DummyCreatedImageList)
            }
        }
    }

    fun openPhotoPicker() = intent {
        postSideEffect(UploadPhotoSideEffect.openPhotoPicker)
    }

    fun requestCameraPermission() = intent {
        postSideEffect(UploadPhotoSideEffect.requestCameraPermission)
    }

    fun setSelectedImageUri(uri: String) = intent {
        reduce {
            state.copy(selectedPhotoUri = uri)
        }
        postSideEffect(UploadPhotoSideEffect.UploadPhotoSuccess)
    }

    fun setSelectedStyle(style: String) = intent {
        if (style == state.selectedStyle) {
            reduce {
                state.copy(selectedStyle = "")
            }
        } else {
            reduce {
                state.copy(selectedStyle = style)
            }
        }
    }

    fun dismissPermissionDialog() = intent {
        reduce {
            state.copy(isPermissionDialogVisible = false)
        }
    }

    fun openCreateImageStopDialog() = intent {
        reduce {
            state.copy(isCreateImageStopDialogVisible = true)
        }
    }

    fun dismissCreateImageStopDialog() = intent {
        reduce {
            state.copy(isCreateImageStopDialogVisible = false)
        }
    }

    fun onPermissionResult(isGranted: Boolean) = intent {
        if (isGranted) {
            postSideEffect(UploadPhotoSideEffect.startCamera)
        } else {
            reduce {
                state.copy(isPermissionDialogVisible = true)
            }
        }
    }

    fun toggleUploadPhotoDialog(flag: Boolean) = intent {
        reduce {
            state.copy(isUploadPhotoDialogVisible = flag)
        }
    }
}
