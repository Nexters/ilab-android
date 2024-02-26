package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.request.SignInRequest
import com.nexters.ilab.android.core.data.response.UserInfoResponse
import com.nexters.ilab.android.core.data.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val service: AuthService,
) : AuthDataSource {
    override suspend fun signIn() {
        service.signIn(SignInRequest())
    }

    override suspend fun getUserInfo(uuid: Long): UserInfoResponse {
        return service.getUserInfo(uuid)
    }

    override suspend fun signOut(uuid: Long) {
        service.signOut(uuid)
    }

    override suspend fun deleteAccount(uuid: Long) {
        service.deleteAccount(uuid)
    }
}
