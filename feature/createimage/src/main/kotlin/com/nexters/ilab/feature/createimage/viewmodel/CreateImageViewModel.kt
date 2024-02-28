package com.nexters.ilab.feature.createimage.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ilab.android.core.common.ErrorHandlerActions
import com.nexters.ilab.android.core.common.handleException
import com.nexters.ilab.android.core.domain.repository.FileRepository
import com.nexters.ilab.feature.createimage.navigation.IMAGE_URL
import com.nexters.ilab.feature.createimage.navigation.STYLE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CreateImageViewModel @Inject constructor(
    private val repository: FileRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<CreateImageState, CreateImageSideEffect>, ErrorHandlerActions {

    override val container = container<CreateImageState, CreateImageSideEffect>(CreateImageState())

    private val imageUrl: String =
        requireNotNull(savedStateHandle.get<String>(IMAGE_URL)) { "imageUrl is required." }
    private val styleId: Int =
        requireNotNull(savedStateHandle.get<Int>(STYLE_ID)) { "styleId is required." }

    init {
        createProfileImage()
    }

    fun createProfileImage() = intent {
        viewModelScope.launch {
            repository.createProfileImage(imageUrl, styleId)
                .onSuccess { createdProfileImageList ->
                    reduce {
                        state.copy(createdProfileImageList = createdProfileImageList.map { it.url }.toImmutableList())
                    }
                    postSideEffect(CreateImageSideEffect.CreateProfileImageSuccess)
                }.onFailure { exception ->
                    handleException(exception, this@CreateImageViewModel)
                }
        }
    }

    fun openCreateProfileImageStopDialog() = intent {
        reduce {
            state.copy(isCreateImageStopDialogVisible = true)
        }
    }

    fun dismissCreateProfileImageStopDialog() = intent {
        reduce {
            state.copy(isCreateImageStopDialogVisible = false)
        }
    }

    fun showToast() = intent {
        postSideEffect(CreateImageSideEffect.ShowToast)
    }

    fun shareCreatedProfileImage() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            val imageUriList = repository.getImageUriList(state.createdProfileImageList)
            reduce {
                state.copy(isLoading = false)
            }
            postSideEffect(CreateImageSideEffect.ShareCreatedImage(imageUriList))
        }
    }

    fun saveCreatedProfileImage() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            repository.saveImageFile(state.createdProfileImageList)
            reduce {
                state.copy(isLoading = false)
            }
            postSideEffect(CreateImageSideEffect.SaveCreatedImageSuccess)
        }
    }

    fun deleteCacheDir() = intent {
        repository.deleteCacheDir()
    }

    override fun openServerErrorDialog() = intent {
        reduce {
            state.copy(isServerErrorDialogVisible = true)
        }
    }

    fun dismissServerErrorDialog() = intent {
        reduce {
            state.copy(isServerErrorDialogVisible = false)
        }
    }

    override fun openNetworkErrorDialog() = intent {
        reduce {
            state.copy(isNetworkErrorDialogVisible = true)
        }
    }

    fun dismissNetworkErrorDialog() = intent {
        reduce {
            state.copy(isNetworkErrorDialogVisible = false)
        }
    }
}
