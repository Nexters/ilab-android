package com.nexters.ilab.android.core.datastore

import kotlinx.coroutines.flow.Flow

interface PrivacyPolicyDataSource {
    val isPrivacyPolicyAgreed: Flow<Boolean>
    suspend fun setPrivacyPolicyAgreement(flag: Boolean)
}
