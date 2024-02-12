package com.nexters.ilab.android.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val TOKEN_DATASTORE = "token_datastore"
private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_DATASTORE)

private const val PRIVACY_POLICY_DATASTORE = "privacy_policy_datastore"
private val Context.privacyPolicyDataStore: DataStore<Preferences> by preferencesDataStore(name = PRIVACY_POLICY_DATASTORE)

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @TokenDataStore
    @Singleton
    @Provides
    internal fun provideTokenDataStore(@ApplicationContext context: Context) = context.tokenDataStore

    @PrivacyPolicyDataStore
    @Singleton
    @Provides
    internal fun providePrivacyPolicyDataStore(@ApplicationContext context: Context) = context.privacyPolicyDataStore
}
