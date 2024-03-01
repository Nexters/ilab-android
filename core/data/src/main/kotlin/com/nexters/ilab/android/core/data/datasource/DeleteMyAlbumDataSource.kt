package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.response.UserInfoResponse

interface DeleteMyAlbumDataSource {
    suspend fun deleteMyAlbum(uuid: Long, thumbnailId: Int): UserInfoResponse
}
