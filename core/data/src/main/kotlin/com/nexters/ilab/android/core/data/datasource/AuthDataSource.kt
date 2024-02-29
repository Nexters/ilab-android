package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.request.AuthRequest
import com.nexters.ilab.android.core.data.response.UserInfoResponse

interface AuthDataSource {
    suspend fun signIn()
    suspend fun getUserInfo(authRequest: AuthRequest): UserInfoResponse
    suspend fun signOut(authRequest: AuthRequest)
    suspend fun deleteAccount(authRequest: AuthRequest)
}
