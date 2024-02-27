package com.nexters.ilab.android.feature.mypage.viewmodel

import com.nexters.ilab.android.core.domain.entity.UserInfoEntity
import com.nexters.ilab.android.core.domain.entity.UserThumbnailEntity

data class MyPageState(
    val isLoading: Boolean = false,
    val userInfo: UserInfoEntity = UserInfoEntity(),
    val selectedMyAlbum: Int = 0,
    val myAlbumFullImageList: List<UserThumbnailEntity> = emptyList(),
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
