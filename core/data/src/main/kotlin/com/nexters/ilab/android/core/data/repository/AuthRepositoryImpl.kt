package com.nexters.ilab.android.core.data.repository

import com.nexters.ilab.android.core.data.datasource.AuthDataSource
import com.nexters.ilab.android.core.data.mapper.toEntity
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
        authDataSource.getUserInfo(uuid).toEntity()
    }

    override suspend fun signOut() = runSuspendCatching {
        authDataSource.signOut()
    }

    override suspend fun deleteAccount() = runSuspendCatching {
        authDataSource.deleteAccount()
    }
}
