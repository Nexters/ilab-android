package com.nexters.ilab.android.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface PrivacyPolicyRepository {
    fun getPrivacyPolicyAgreement(): Flow<Boolean>
    suspend fun setPrivacyPolicyAgreement(flag: Boolean)
}
