package com.nexters.ilab.android.feature.login.di

import android.app.Activity
import android.content.Intent
import com.nexters.ilab.android.core.common.extension.startActivityWithAnimation
import com.nexters.ilab.android.feature.login.LoginActivity
import com.nexters.ilab.android.feature.navigator.LoginNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class LoginNavigatorImpl @Inject constructor() : LoginNavigator {
    override fun navigateFrom(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent,
    ) {
        activity.startActivityWithAnimation<LoginActivity>(
            withFinish = withFinish,
            intentBuilder = intentBuilder,
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LoginNavigatorModule {
    @Singleton
    @Binds
    abstract fun bindLoginNavigator(loginNavigatorImpl: LoginNavigatorImpl): LoginNavigator
}
