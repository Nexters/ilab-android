package com.nexters.ilab.android.feature.myalbum.viewmodel

sealed interface MyAlbumSideEffect {
    data class ShareMyAlbum(val imageUriList: List<String>) : MyAlbumSideEffect
    data object SaveMyAlbumSuccess : MyAlbumSideEffect
}
