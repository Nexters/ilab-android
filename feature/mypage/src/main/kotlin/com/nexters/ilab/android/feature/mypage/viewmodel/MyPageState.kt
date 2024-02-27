package com.nexters.ilab.android.feature.mypage.viewmodel

import com.nexters.ilab.android.core.domain.entity.UserInfoEntity
import com.nexters.ilab.android.core.domain.entity.UserThumbnail

data class MyPageState(
    val isLoading: Boolean = false,
    val userInfo: UserInfoEntity = UserInfoEntity(),
    val selectedMyAlbum: Int = 0,
    val myAlbumFullImageList: List<UserThumbnail> = emptyList(),
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
