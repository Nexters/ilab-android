package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.request.AuthRequest
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

    override suspend fun getUserInfo(authRequest: AuthRequest): UserInfoResponse {
        return service.getUserInfo(authRequest)
    }

    override suspend fun signOut(authRequest: AuthRequest) {
        service.signOut(authRequest)
    }

    override suspend fun deleteAccount(authRequest: AuthRequest) {
        service.deleteAccount(authRequest)
    }
}
