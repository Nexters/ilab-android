package com.nexters.ilab.android.core.data.di

import com.nexters.ilab.android.core.data.repository.FileRepositoryImpl
import com.nexters.ilab.android.core.data.repository.PrivacyPolicyRepositoryImpl
import com.nexters.ilab.android.core.domain.repository.FileRepository
import com.nexters.ilab.android.core.domain.repository.PrivacyPolicyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository

    @Binds
    @Singleton
    abstract fun bindPrivacyPolicyRepository(privacyPolicyRepositoryImpl: PrivacyPolicyRepositoryImpl): PrivacyPolicyRepository
}
