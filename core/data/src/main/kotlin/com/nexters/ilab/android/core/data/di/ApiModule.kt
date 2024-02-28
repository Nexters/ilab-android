package com.nexters.ilab.android.core.data.di

import com.nexters.ilab.android.core.data.service.ILabService
import com.nexters.ilab.android.core.data.service.AuthService
import com.nexters.ilab.android.core.network.di.ILabApi
import com.nexters.ilab.android.core.network.di.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Singleton
    @Provides
    internal fun provideILabService(
        @ILabApi retrofit: Retrofit,
    ): ILabService {
        return retrofit.create(ILabService::class.java)
    }

    @Singleton
    @Provides
    internal fun provideAuthService(
        @LoginApi retrofit: Retrofit,
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}
