package com.nexters.ilab.android.core.data.di

import com.nexters.ilab.android.core.data.service.ILabService
import com.nexters.ilab.android.core.data.service.LoginService
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
    internal fun provideLoginService(
        @LoginApi retrofit: Retrofit,
    ): LoginService {
        return retrofit.create(LoginService::class.java)
    }
}
