package com.nexters.ilab.android.feature.mypage.viewmodel

import com.nexters.ilab.android.core.common.UiText

sealed interface MyPageSideEffect {
    data class ShareMyAlbum(val imageUriList: List<String>) : MyPageSideEffect
    data class NavigateToMyAlbum(
        val imageUrlList: List<String>,
        val imageStyle: String,
    ) : MyPageSideEffect

    data class ShowToast(val message: UiText) : MyPageSideEffect
}
