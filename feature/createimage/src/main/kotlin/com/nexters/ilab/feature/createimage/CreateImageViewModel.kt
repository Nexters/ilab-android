package com.nexters.ilab.feature.createimage

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
import javax.inject.Inject

@HiltViewModel
class CreateImageViewModel @Inject constructor(
    private val fileRepository: FileRepository,
) : ViewModel(), ContainerHost<CreateImageState, CreateImageSideEffect> {

    override val container = container<CreateImageState, CreateImageSideEffect>(CreateImageState())

    init {
        intent {
            reduce {
                state.copy(createdImageList = DummyCreatedImageUrls)
            }
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

    fun shareCreatedImage() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            val imageUriList = fileRepository.getImageUriList(state.createdImageList)
            reduce {
                state.copy(isLoading = false)
            }
            postSideEffect(CreateImageSideEffect.ShareCreatedImage(imageUriList))
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
            postSideEffect(CreateImageSideEffect.SaveCreatedImageSuccess)
        }
    }

    fun deleteCacheDir() = intent {
        fileRepository.deleteCacheDir()
    }
}
