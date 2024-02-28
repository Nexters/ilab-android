package com.nexters.ilab.android.core.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ILabApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoginApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FileApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ILabClient
