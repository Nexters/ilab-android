package com.nexters.ilab.android.feature.mypage.viewmodel

sealed interface MyPageSideEffect {
    data class ShareMyAlbum(val imageUriList: List<String>) : MyPageSideEffect
    data class NavigateToMyAlbum(
        val imageUrlList: List<String>,
        val imageStyle: String,
    ) : MyPageSideEffect
}
