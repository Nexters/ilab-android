package com.nexters.ilab.android.feature.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container = container<HomeState, HomeSideEffect>(HomeState())

    // for test
    val dummyStyleImageList: List<ProfileImage> = listOf(
        ProfileImage("https://picsum.photos/200/266", "몽환적인"),
        ProfileImage("https://picsum.photos/200/266", "자연적인"),
        ProfileImage("https://picsum.photos/200/266", "스케치"),
        ProfileImage("https://picsum.photos/200/266", "고독한"),
    )

    val dummyProfileImageList: List<ProfileImage> = listOf(
        ProfileImage("https://picsum.photos/162/336", "몽환적인"),
        ProfileImage("https://picsum.photos/162/336", "자연적인"),
        ProfileImage("https://picsum.photos/162/336", "몽환적인"),
        ProfileImage("https://picsum.photos/162/336", "자연적인"),
        ProfileImage("https://picsum.photos/162/336", "몽환적인"),
        ProfileImage("https://picsum.photos/162/336", "자연적인"),
        ProfileImage("https://picsum.photos/162/336", "몽환적인"),
        ProfileImage("https://picsum.photos/162/336", "자연적인"),
        ProfileImage("https://picsum.photos/162/336", "몽환적인"),
        ProfileImage("https://picsum.photos/162/336", "자연적인"),
        ProfileImage("https://picsum.photos/162/336", "몽환적인"),
        ProfileImage("https://picsum.photos/162/336", "자연적인"),
    )

    init {
        intent {
            reduce {
                state.copy(styleImageList = dummyStyleImageList)
            }
            reduce {
                state.copy(profileImageList = dummyProfileImageList)
            }
        }
    }

    fun openProfileImageDialog(index: Int) = intent {
        reduce {
            state.copy(isProfileImageDialogVisible = true)
        }
        reduce {
            state.copy(selectedIndex = index)
        }
    }

    fun dismissProfileImageDialog() = intent {
        reduce {
            state.copy(isProfileImageDialogVisible = false)
        }
    }
}
