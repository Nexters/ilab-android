package com.nexters.ilab.android.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.nexters.ilab.android.core.datastore.TokenDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val TOKEN_DATASTORE = "token_datastore"
private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_DATASTORE)

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Singleton
    @Provides
    internal fun providePreferencesDataStore(@ApplicationContext context: Context) = context.tokenDataStore

    @Singleton
    @Provides
    internal fun provideTokenDataStore(dataStore: DataStore<Preferences>) = TokenDataStoreImpl(dataStore)
}
