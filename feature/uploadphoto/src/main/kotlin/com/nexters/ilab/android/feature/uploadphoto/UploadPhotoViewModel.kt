package com.nexters.ilab.android.feature.uploadphoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ilab.android.core.domain.repository.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UploadPhotoViewModel @Inject constructor(
    private val fileRepository: FileRepository,
) : ViewModel(), ContainerHost<UploadPhotoState, UploadPhotoSideEffect> {

    override val container = container<UploadPhotoState, UploadPhotoSideEffect>(UploadPhotoState())

    init {
        intent {
            reduce {
                state.copy(createdImageList = DummyCreatedImageUrls)
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

    fun shareCreatedImage() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            val imageUriList = fileRepository.getImageUriList(state.createdImageList)
            reduce {
                state.copy(isLoading = false)
            }
            postSideEffect(UploadPhotoSideEffect.ShareCreatedImage(imageUriList))
        }
    }

    fun saveCreatedImage() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            fileRepository.saveImageFile(state.createdImageList)
            reduce {
                state.copy(isLoading = false)
            }
            postSideEffect(UploadPhotoSideEffect.SaveCreatedImageSuccess)
        }
    }

    fun deleteCacheDir() = intent {
        Timber.d("deleteCacheDir() called")
        fileRepository.deleteCacheDir()
    }
}
