package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.response.UserInfoResponse

interface AuthDataSource {
    suspend fun signIn()
    suspend fun getUserInfo(uuid: Long): UserInfoResponse
    suspend fun signOut()
    suspend fun deleteAccount()
}
