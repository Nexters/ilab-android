package com.nexters.ilab.android.core.data.repository

import com.nexters.ilab.android.core.datastore.PrivacyPolicyDataSource
import com.nexters.ilab.android.core.domain.repository.PrivacyPolicyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PrivacyPolicyRepositoryImpl @Inject constructor(
    private val privacyPolicyDataSource: PrivacyPolicyDataSource,
) : PrivacyPolicyRepository {
    override fun getPrivacyPolicyAgreement(): Flow<Boolean> =
        privacyPolicyDataSource.isPrivacyPolicyAgreed

    override suspend fun setPrivacyPolicyAgreement(flag: Boolean) {
        privacyPolicyDataSource.setPrivacyPolicyAgreement(flag)
    }
}
