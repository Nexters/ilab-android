package com.nexters.ilab.android.core.data.repository

import com.nexters.ilab.android.core.data.datasource.AuthDataSource
import com.nexters.ilab.android.core.data.util.runSuspendCatching
import com.nexters.ilab.android.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun signIn() = runSuspendCatching {
        dataSource.signIn()
    }

    override suspend fun signOut() = runSuspendCatching {
        dataSource.signOut()
    }

    override suspend fun deleteAccount() = runSuspendCatching {
        dataSource.deleteAccount()
    }
}
