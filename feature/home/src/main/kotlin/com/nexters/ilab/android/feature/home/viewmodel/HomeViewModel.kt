package com.nexters.ilab.android.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ilab.android.core.domain.entity.ProfileEntity
import com.nexters.ilab.android.core.domain.repository.StyleRepository
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
class HomeViewModel @Inject constructor(
    private val styleRepository: StyleRepository,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container = container<HomeState, HomeSideEffect>(HomeState())

    // for test
    val dummyProfileImageList: List<ProfileEntity> = listOf(
        ProfileEntity("1", "https://picsum.photos/162/336", "느와르"),
        ProfileEntity("2", "https://picsum.photos/162/336", "경성"),
        ProfileEntity("3", "https://picsum.photos/162/336", "일본애니"),
        ProfileEntity("4", "https://picsum.photos/162/336", "반고흐"),
        ProfileEntity("5", "https://picsum.photos/162/336", "고독한"),
        ProfileEntity("6", "https://picsum.photos/162/336", "몽환적인"),
        ProfileEntity("7", "https://picsum.photos/162/336", "몽환적인"),
        ProfileEntity("8", "https://picsum.photos/162/336", "몽환적인"),
        ProfileEntity("9", "https://picsum.photos/162/336", "몽환적인"),
        ProfileEntity("10", "https://picsum.photos/162/336", "몽환적인"),
        ProfileEntity("11", "https://picsum.photos/162/336", "몽환적인"),
        ProfileEntity("12", "https://picsum.photos/162/336", "몽환적인"),
    )

    init {
        getStyleList()
        // todo: getProfileList
        intent {
            reduce {
                state.copy(profileImageList = dummyProfileImageList)
            }
        }
    }

    fun getStyleList() = intent {
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

    fun setSelectedStyleImage(index: Int) = intent {
        if (index in 0..<state.styleImageList.size) {
            reduce {
                state.copy(selectedStyle = state.styleImageList[index])
            }
        }
    }

    fun onGenerateImageClick() = intent {
        postSideEffect(HomeSideEffect.NavigateToUploadPhoto(state.selectedStyle.name))
    }

    fun openProfileImageDialog(index: Int) = intent {
        reduce {
            state.copy(isProfileImageDialogVisible = true)
        }
        if (index in 0..<state.profileImageList.size) {
            reduce {
                state.copy(selectedProfileEntity = state.profileImageList[index])
            }
        }
    }

    fun dismissProfileImageDialog() = intent {
        reduce {
            state.copy(isProfileImageDialogVisible = false)
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
