package com.nexters.ilab.android.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ilab.android.core.common.ErrorHandlerActions
import com.nexters.ilab.android.core.common.handleException
import com.nexters.ilab.android.core.domain.repository.ProfileRepository
import com.nexters.ilab.android.core.domain.repository.StyleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val styleRepository: StyleRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect>, ErrorHandlerActions {
    override val container = container<HomeState, HomeSideEffect>(HomeState())

    init {
        getStyleProfileList()
    }

    fun getStyleProfileList() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            styleRepository.getStyleList()
                .onSuccess { styleImageList ->
                    val endIndex = if (styleImageList.size < 4) styleImageList.size - 1 else 3
                    reduce {
                        state.copy(styleImageList = styleImageList.shuffled().slice(0..endIndex))
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@HomeViewModel)
                }

            profileRepository.getProfileList()
                .onSuccess { profileImageList ->
                    reduce {
                        state.copy(profileImageList = profileImageList)
                    }
                }
                .onFailure { exception ->
                    handleException(exception, this@HomeViewModel)
                }
            reduce {
                state.copy(isLoading = false)
            }
        }
    }

    fun setSelectedStyleImage(index: Int) = intent {
        if (index in 0..<state.styleImageList.size) {
            reduce {
                state.copy(selectedStyle = state.styleImageList[index])
            }
        }
    }

    private fun setSelectedProfileImage(index: Int) = intent {
        if (index in 0..<state.profileImageList.size) {
            reduce {
                state.copy(selectedProfileImage = state.profileImageList[index])
            }
        }
    }

    fun onCreateImageButtonClickFromStyle() = intent {
        postSideEffect(
            HomeSideEffect.OnCreateImageButtonClickFromStyle(
                if (state.selectedStyle.name.isEmpty()) {
                    state.styleImageList[0].name
                } else {
                    state.selectedStyle.name
                },
            ),
        )
    }

    fun onCreateImageButtonClickFromProfile() = intent {
        postSideEffect(
            HomeSideEffect.OnCreateImageButtonClickFromProfile(state.selectedProfileImage.name),
        )
    }

    fun openProfileImageDialog(index: Int) = intent {
        reduce {
            state.copy(isProfileImageDialogVisible = true)
        }
        setSelectedProfileImage(index)
    }

    fun dismissProfileImageDialog() = intent {
        reduce {
            state.copy(isProfileImageDialogVisible = false)
        }
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
