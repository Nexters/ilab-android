package com.nexters.ilab.android.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nexters.ilab.android.core.datastore.TokenDataSource
import com.nexters.ilab.android.core.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val MaxTimeoutMillis = 60_000L

private val jsonRule = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    prettyPrint = true
    isLenient = true
}

private val jsonConverterFactory = jsonRule.asConverterFactory("application/json".toMediaType())
private val fileConverterFactory = jsonRule.asConverterFactory("multipart/form-data".toMediaType())

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Singleton
    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("ApiService").d(message)
        }.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    internal fun provideTokenInterceptor(
        dataStore: TokenDataSource,
    ): TokenInterceptor {
        return TokenInterceptor(dataStore)
    }

    @ILabClient
    @Singleton
    @Provides
    internal fun provideILabOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(MaxTimeoutMillis, TimeUnit.MILLISECONDS)
            .readTimeout(MaxTimeoutMillis, TimeUnit.MILLISECONDS)
            .writeTimeout(MaxTimeoutMillis, TimeUnit.MILLISECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @TokenClient
    @Singleton
    @Provides
    internal fun provideTokenOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(MaxTimeoutMillis, TimeUnit.MILLISECONDS)
            .readTimeout(MaxTimeoutMillis, TimeUnit.MILLISECONDS)
            .writeTimeout(MaxTimeoutMillis, TimeUnit.MILLISECONDS)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @ILabApi
    @Singleton
    @Provides
    internal fun provideILabApiRetrofit(
        @ILabClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jsonConverterFactory)
            .build()
    }

    @LoginApi
    @Singleton
    @Provides
    internal fun provideAuthApiRetrofit(
        @TokenClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jsonConverterFactory)
            .build()
    }

    @FileApi
    @Singleton
    @Provides
    internal fun provideFileApiRetrofit(
        @ILabClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(fileConverterFactory)
            .build()
    }
}
