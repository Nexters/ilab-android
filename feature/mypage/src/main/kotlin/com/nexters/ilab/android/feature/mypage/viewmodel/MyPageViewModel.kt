package com.nexters.ilab.android.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ilab.android.core.common.ErrorHandlerActions
import com.nexters.ilab.android.core.common.handleException
import com.nexters.ilab.android.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel(), ContainerHost<MyPageState, MyPageSideEffect>, ErrorHandlerActions {
    override val container = container<MyPageState, MyPageSideEffect>(MyPageState())

    // for test
    val DummyMyAlbumList: List<MyAlbum> = listOf(
        MyAlbum(setDummyImageList(1), "몽환적인"),
        MyAlbum(setDummyImageList(2), "자연적인"),
        MyAlbum(setDummyImageList(3), "스케치"),
        MyAlbum(setDummyImageList(4), "고독한"),
        MyAlbum(setDummyImageList(1), "몽환적인"),
        MyAlbum(setDummyImageList(2), "자연적인"),
        MyAlbum(setDummyImageList(3), "스케치"),
        MyAlbum(setDummyImageList(4), "고독한"),
        MyAlbum(setDummyImageList(1), "몽환적인"),
        MyAlbum(setDummyImageList(2), "자연적인"),
        MyAlbum(setDummyImageList(3), "스케치"),
        MyAlbum(setDummyImageList(4), "고독한"),
    )

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
                        state.copy(userInfo = userInfo)
                    }
                }.onFailure { exception ->
                    handleException(exception, this@MyPageViewModel)
                }
            reduce {
                state.copy(
                    isLoading = false,
                    myAlbumImageList = DummyMyAlbumList,
                )
            }
        }
    }

    fun setSelectedMyAlbum(index: Int) = intent {
        reduce {
            state.copy(selectedMyAlbum = index)
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

internal fun setDummyImageList(index: Int): List<Pair<String, String>> {
    return when (index) {
        1 -> listOf(
            "https://picsum.photos/id/49/1280/792" to "Created Image Example 1",
            "https://picsum.photos/id/53/1280/1280" to "Created Image Example 2",
            "https://picsum.photos/id/54/3264/2176" to "Created Image Example 3",
            "https://picsum.photos/id/58/1280/853" to "Created Image Example 4",
        )

        2 -> listOf(
            "https://picsum.photos/id/53/1280/1280" to "Created Image Example 2",
            "https://picsum.photos/id/54/3264/2176" to "Created Image Example 3",
            "https://picsum.photos/id/58/1280/853" to "Created Image Example 4",
            "https://picsum.photos/id/49/1280/792" to "Created Image Example 1",
        )

        3 -> listOf(
            "https://picsum.photos/id/54/3264/2176" to "Created Image Example 3",
            "https://picsum.photos/id/58/1280/853" to "Created Image Example 4",
            "https://picsum.photos/id/49/1280/792" to "Created Image Example 1",
            "https://picsum.photos/id/53/1280/1280" to "Created Image Example 2",
        )

        4 -> listOf(
            "https://picsum.photos/id/58/1280/853" to "Created Image Example 4",
            "https://picsum.photos/id/54/3264/2176" to "Created Image Example 3",
            "https://picsum.photos/id/53/1280/1280" to "Created Image Example 2",
            "https://picsum.photos/id/49/1280/792" to "Created Image Example 1",
        )

        else -> listOf(
            "https://picsum.photos/id/54/3264/2176" to "Created Image Example 3",
            "https://picsum.photos/id/58/1280/853" to "Created Image Example 4",
            "https://picsum.photos/id/49/1280/792" to "Created Image Example 1",
            "https://picsum.photos/id/53/1280/1280" to "Created Image Example 2",
        )
    }
}
