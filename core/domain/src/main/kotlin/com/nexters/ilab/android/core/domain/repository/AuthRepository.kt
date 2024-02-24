package com.nexters.ilab.android.core.domain.repository

interface AuthRepository {
    suspend fun signIn(): Result<Unit>
    suspend fun signOut(): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
}
