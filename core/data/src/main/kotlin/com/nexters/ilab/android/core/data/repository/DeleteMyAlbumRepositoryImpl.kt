package com.nexters.ilab.android.core.data.repository

import com.nexters.ilab.android.core.data.datasource.DeleteMyAlbumDataSource
import com.nexters.ilab.android.core.data.mapper.toEntity
import com.nexters.ilab.android.core.data.util.runSuspendCatching
import com.nexters.ilab.android.core.datastore.TokenDataSource
import com.nexters.ilab.android.core.domain.repository.DeleteMyAlbumRepository
import javax.inject.Inject

class DeleteMyAlbumRepositoryImpl @Inject constructor(
    private val tokenDataSource: TokenDataSource,
    private val myAlbumDataSource: DeleteMyAlbumDataSource,
) : DeleteMyAlbumRepository {
    override suspend fun deleteMyAlbum(thumbnailId: Int) = runSuspendCatching {
        val uuid = tokenDataSource.getUUID()
        myAlbumDataSource.deleteMyAlbum(uuid, thumbnailId).toEntity()
    }
}
