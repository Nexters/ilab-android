package com.nexters.ilab.android.core.data.repository

import com.nexters.ilab.android.core.data.datasource.AuthDataSource
import com.nexters.ilab.android.core.data.mapper.toEntity
import com.nexters.ilab.android.core.data.request.AuthRequest
import com.nexters.ilab.android.core.data.util.runSuspendCatching
import com.nexters.ilab.android.core.datastore.TokenDataSource
import com.nexters.ilab.android.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val tokenDataSource: TokenDataSource,
) : AuthRepository {
    override suspend fun signIn() = runSuspendCatching {
        authDataSource.signIn()
    }

    override suspend fun getUserInfo() = runSuspendCatching {
        val uuid = tokenDataSource.getUUID()
        authDataSource.getUserInfo(AuthRequest(uuid)).toEntity()
    }

    override suspend fun signOut() = runSuspendCatching {
        val uuid = tokenDataSource.getUUID()
        authDataSource.signOut(AuthRequest(uuid))
    }

    override suspend fun deleteAccount() = runSuspendCatching {
        val uuid = tokenDataSource.getUUID()
        authDataSource.deleteAccount(AuthRequest(uuid))
    }
}
