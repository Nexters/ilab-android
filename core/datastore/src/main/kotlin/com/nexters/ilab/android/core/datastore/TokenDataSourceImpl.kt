package com.nexters.ilab.android.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nexters.ilab.android.core.datastore.di.TokenDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class TokenDataSourceImpl @Inject constructor(
    @TokenDataStore val dataStore: DataStore<Preferences>,
) : TokenDataSource {
    private companion object {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val KEY_UUID = longPreferencesKey("uuid")
    }

    override suspend fun setAccessToken(accessToken: String) {
        dataStore.edit { preferences -> preferences[KEY_ACCESS_TOKEN] = accessToken }
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        dataStore.edit { preferences -> preferences[KEY_REFRESH_TOKEN] = refreshToken }
    }

    override suspend fun setUUID(uuid: Long) {
        dataStore.edit { preferences -> preferences[KEY_UUID] = uuid }
    }

    override suspend fun getAccessToken(): String = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_ACCESS_TOKEN] ?: ""

    override suspend fun getUUID(): Long = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_UUID] ?: 0L

    override suspend fun getRefreshToken(): String = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_REFRESH_TOKEN] ?: ""

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
