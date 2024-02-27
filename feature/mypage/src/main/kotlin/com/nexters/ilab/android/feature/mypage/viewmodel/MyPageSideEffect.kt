package com.nexters.ilab.android.feature.mypage.viewmodel

sealed interface MyPageSideEffect {
    data class ShareMyAlbumImage(val imageUriList: List<String>) : MyPageSideEffect
    data object SaveMyAlbumImageSuccess : MyPageSideEffect
}
