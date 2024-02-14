package com.nexters.ilab.android.core.data.di

import com.nexters.ilab.android.core.data.datasource.FileDataSource
import com.nexters.ilab.android.core.data.datasource.FileDataSourceImpl
import com.nexters.ilab.android.core.datastore.PrivacyPolicyDataSource
import com.nexters.ilab.android.core.datastore.PrivacyPolicyDataSourceImpl
import com.nexters.ilab.android.core.datastore.TokenDataSource
import com.nexters.ilab.android.core.datastore.TokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindFileDataSource(fileDataSourceImpl: FileDataSourceImpl): FileDataSource

    @Singleton
    @Binds
    abstract fun bindTokenDataSource(tokenDataSourceImpl: TokenDataSourceImpl): TokenDataSource

    @Singleton
    @Binds
    abstract fun bindPrivacyPolicyDataSource(privacyPolicyDataSourceImpl: PrivacyPolicyDataSourceImpl): PrivacyPolicyDataSource
}
