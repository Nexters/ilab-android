package com.nexters.ilab.android.core.data.di

import com.nexters.ilab.android.core.data.datasource.FileDataSource
import com.nexters.ilab.android.core.data.datasource.FileDataSourceImpl
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
}
