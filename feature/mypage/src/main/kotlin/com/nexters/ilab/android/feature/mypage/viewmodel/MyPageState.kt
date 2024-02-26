package com.nexters.ilab.android.feature.mypage.viewmodel

import com.nexters.ilab.android.core.domain.entity.UserInfoEntity

data class MyAlbum(
    val myAlbumImage: List<Pair<String, String>> = emptyList(),
    val myAlbumKeyword: String = "",
)

data class MyPageState(
    val isLoading: Boolean = false,
    val userInfo: UserInfoEntity = UserInfoEntity(),
    val selectedMyAlbum: Int = 0,
    val myAlbumImageList: List<MyAlbum> = emptyList(),
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
