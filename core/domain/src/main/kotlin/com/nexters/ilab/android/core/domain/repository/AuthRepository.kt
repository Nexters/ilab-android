package com.nexters.ilab.android.core.domain.repository

import com.nexters.ilab.android.core.domain.entity.UserInfoEntity

interface AuthRepository {
    suspend fun signIn(): Result<Unit>
    suspend fun getUserInfo(): Result<UserInfoEntity>
    suspend fun signOut(): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
}
