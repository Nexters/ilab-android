package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.response.UserInfoResponse
import com.nexters.ilab.android.core.data.service.ILabService
import javax.inject.Inject

class DeleteMyAlbumDataSourceImpl @Inject constructor(
    private val service: ILabService,
) : DeleteMyAlbumDataSource {
    override suspend fun deleteMyAlbum(uuid: Long, thumbnailId: Int): UserInfoResponse {
        return service.deleteMyAlbumImage(uuid, thumbnailId)
    }
}
