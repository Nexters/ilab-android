package com.nexters.ilab.android.core.datastore

interface TokenDataStore {
    suspend fun setAccessToken(accessToken: String)

    suspend fun setRefreshToken(refreshToken: String)

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun clear()
}
