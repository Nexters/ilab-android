package com.nexters.ilab.android.feature.uploadphoto.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ilab.android.core.domain.repository.PrivacyPolicyRepository
import com.nexters.ilab.android.core.domain.repository.StyleRepository
import com.nexters.ilab.android.feature.uploadphoto.navigation.SELECTED_STYLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class UploadPhotoViewModel @Inject constructor(
    private val privacyPolicyRepository: PrivacyPolicyRepository,
    private val styleRepository: StyleRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<UploadPhotoState, UploadPhotoSideEffect> {

    override val container = container<UploadPhotoState, UploadPhotoSideEffect>(UploadPhotoState())
    private val selectedStyle = savedStateHandle[SELECTED_STYLE] ?: ""

    init {
        Timber.d("selectedStyle: $selectedStyle")
        observePrivacyPolicyAgreement()
        getStyleList()
        setSelectedStyle(selectedStyle)
    }

    private fun observePrivacyPolicyAgreement() = intent {
        viewModelScope.launch {
            privacyPolicyRepository.getPrivacyPolicyAgreement().collect { isAgreed ->
                reduce { state.copy(isPrivacyPolicyAgreed = isAgreed) }
            }
        }
    }

    fun getStyleList() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            styleRepository.getStyleList()
                .onSuccess { styleList ->
                    Timber.d("$styleList")
                    reduce {
                        state.copy(styleList = styleList)
                    }
                }
                .onFailure { exception ->
                    when (exception) {
                        is HttpException -> {
                            if (exception.code() == 500) {
                                openServerErrorDialog()
                            } else {
                                Timber.e(exception)
                            }
                        }
                        is UnknownHostException -> {
                            openNetworkErrorDialog()
                        }
                        is SocketTimeoutException -> {
                            openServerErrorDialog()
                        }
                        else -> {
                            Timber.e(exception)
                        }
                    }
                }
            reduce {
                state.copy(isLoading = false)
            }
        }
    }

    fun togglePrivacyPolicyAgreement(flag: Boolean) {
        viewModelScope.launch {
            privacyPolicyRepository.setPrivacyPolicyAgreement(flag)
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

    private fun openServerErrorDialog() = intent {
        reduce {
            state.copy(isServerErrorDialogVisible = true)
        }
    }

    fun dismissServerErrorDialog() = intent {
        reduce {
            state.copy(isServerErrorDialogVisible = false)
        }
    }

    private fun openNetworkErrorDialog() = intent {
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
