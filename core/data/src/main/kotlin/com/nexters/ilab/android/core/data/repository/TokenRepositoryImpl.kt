package com.nexters.ilab.android.core.data.repository

import com.nexters.ilab.android.core.datastore.TokenDataSource
import com.nexters.ilab.android.core.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val dataSource: TokenDataSource,
) : TokenRepository {
    override suspend fun setAccessToken(accessToken: String) {
        dataSource.setAccessToken(accessToken)
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        dataSource.setRefreshToken(refreshToken)
    }

    override suspend fun setUUID(uuid: Long) {
        dataSource.setUUID(uuid)
    }

    override suspend fun getAccessToken(): String {
        return dataSource.getAccessToken()
    }

    override suspend fun getRefreshToken(): String {
        return dataSource.getRefreshToken()
    }

    override suspend fun getUUID(): Long {
        return dataSource.getUUID()
    }

    override suspend fun clear() {
        dataSource.clear()
    }
}
