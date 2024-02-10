package com.nexters.ilab.android.feature.uploadphoto

import androidx.lifecycle.ViewModel
import com.nexters.ilab.android.core.domain.repository.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class UploadPhotoViewModel @Inject constructor(
    private val fileRepository: FileRepository,
) : ViewModel(), ContainerHost<UploadPhotoState, UploadPhotoSideEffect> {

    override val container = container<UploadPhotoState, UploadPhotoSideEffect>(UploadPhotoState())

    init {
        intent {
            reduce {
                state.copy(createdImageList = DummyCreatedImageList)
            }
        }
    }

    fun openPhotoPicker() = intent {
        postSideEffect(UploadPhotoSideEffect.OpenPhotoPicker)
    }

    fun requestCameraPermission() = intent {
        postSideEffect(UploadPhotoSideEffect.RequestCameraPermission)
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
            postSideEffect(UploadPhotoSideEffect.StartCamera)
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

    fun saveCreatedImage(imageInfoList: List<Pair<String, ByteArray>>) = intent {
        reduce {
            state.copy(isLoading = true)
        }
        imageInfoList.forEach { (fileName, byteArray) ->
            fileRepository.saveImageFile(
                fileName = fileName,
                byteArray = byteArray,
            )
        }
        postSideEffect(UploadPhotoSideEffect.SaveCreatedImageSuccess)
        reduce {
            state.copy(isLoading = false)
        }
    }
}
