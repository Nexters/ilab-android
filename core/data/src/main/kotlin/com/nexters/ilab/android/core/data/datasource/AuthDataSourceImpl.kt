package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.request.SignInRequest
import com.nexters.ilab.android.core.data.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val service: AuthService,
) : AuthDataSource {
    override suspend fun signIn() {
        service.signIn(SignInRequest())
    }

    override suspend fun signOut() {
        service.signOut()
    }

    override suspend fun deleteAccount() {
        service.deleteAccount()
    }
}
