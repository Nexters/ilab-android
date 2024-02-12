package com.nexters.ilab.android.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.nexters.ilab.android.core.datastore.di.PrivacyPolicyDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrivacyPolicyDataSourceImpl @Inject constructor(
    @PrivacyPolicyDataStore private val dataStore: DataStore<Preferences>,
) : PrivacyPolicyDataSource {
    private companion object {
        private val KEY_PRIVACY_POLICY_AGREEMENT = booleanPreferencesKey("privacy_policy_agreement")
    }

    override val isPrivacyPolicyAgreed = dataStore.data.map { preferences ->
        preferences[KEY_PRIVACY_POLICY_AGREEMENT] ?: false
    }

    override suspend fun setPrivacyPolicyAgreement(flag: Boolean) {
        dataStore.edit { preferences -> preferences[KEY_PRIVACY_POLICY_AGREEMENT] = flag }
    }
}
