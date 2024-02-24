package com.nexters.ilab.android.core.data.datasource

interface AuthDataSource {
    suspend fun signIn()
    suspend fun signOut()
    suspend fun deleteAccount()
}
