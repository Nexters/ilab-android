package com.nexters.ilab.android.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ilab.android.core.common.ErrorHandlerActions
import com.nexters.ilab.android.core.common.handleException
import com.nexters.ilab.android.core.domain.repository.AuthRepository
import com.nexters.ilab.android.core.domain.repository.DeleteMyAlbumRepository
import com.nexters.ilab.android.core.domain.repository.FileRepository
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
class MyPageViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val fileRepository: FileRepository,
    private val deleteMyAlbumRepository: DeleteMyAlbumRepository,
) : ViewModel(), ContainerHost<MyPageState, MyPageSideEffect>, ErrorHandlerActions {
    override val container = container<MyPageState, MyPageSideEffect>(MyPageState())

    init {
        getUserInfo()
    }

    fun getUserInfo() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            authRepository.getUserInfo()
                .onSuccess { userInfo ->
                    reduce {
                        state.copy(
                            userInfo = userInfo,
                            myAlbumFullImageList = userInfo.thumbnails.toImmutableList(),
                        )
                    }
                }.onFailure { exception ->
                    handleException(exception, this@MyPageViewModel)
                }
            reduce {
                state.copy(
                    isLoading = false,
                )
            }
        }
    }

    fun deleteMyAlbum(index: Int) = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            deleteMyAlbumRepository.deleteMyAlbum(index)
                .onSuccess { userInfo ->
                    reduce {
                        state.copy(
                            myAlbumFullImageList = userInfo.thumbnails.toImmutableList(),
                        )
                    }
                }.onFailure { exception ->
                    handleException(exception, this@MyPageViewModel)
                }
            reduce {
                state.copy(
                    isLoading = false,
                )
            }
        }
    }

    fun shareMyAlbum() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            val imageList: MutableList<String> = mutableListOf()
            state.myAlbumFullImageList[state.selectedMyAlbum].images.forEach { userAlbumImage ->
                imageList.add(userAlbumImage.imageUrl)
            }
            val imageUriList = fileRepository.getImageUriList(imageList)
            reduce {
                state.copy(isLoading = false)
            }
            postSideEffect(MyPageSideEffect.ShareMyAlbum(imageUriList))
        }
    }

    fun deleteCacheDir() = intent {
        fileRepository.deleteCacheDir()
    }

    fun onAlbumClick(index: Int) = intent {
        reduce {
            state.copy(selectedMyAlbum = index)
        }
        postSideEffect(
            MyPageSideEffect.NavigateToMyAlbum(
                state.myAlbumFullImageList[state.selectedMyAlbum].images.map { it.imageUrl },
                state.myAlbumFullImageList[state.selectedMyAlbum].images.map { it.imageStyle.name }.first(),
            ),
        )
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
