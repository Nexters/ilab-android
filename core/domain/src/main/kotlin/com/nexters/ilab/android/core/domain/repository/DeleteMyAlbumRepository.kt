package com.nexters.ilab.android.core.domain.repository

import com.nexters.ilab.android.core.domain.entity.UserInfoEntity

interface DeleteMyAlbumRepository {
    suspend fun deleteMyAlbum(thumbnailId: Int): Result<UserInfoEntity>
}
