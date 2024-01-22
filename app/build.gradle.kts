@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.application)
    alias(libs.plugins.ilab.android.application.compose)
    alias(libs.plugins.ilab.android.hilt)
}

android {
    namespace = "com.nexters.ilab.android"

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementations(
        projects.core.common,
        projects.core.data,
        projects.core.designsystem,
        projects.core.domain,
        projects.core.network,
        projects.core.datastore,
        projects.core.ui,

        projects.feature.camera,
        projects.feature.home,
        projects.feature.intro,
        projects.feature.login,
        projects.feature.main,
        projects.feature.navigator,
        projects.feature.mypage,
        projects.feature.setting,

        libs.androidx.activity.compose,
        libs.androidx.core,
        libs.androidx.splash,
        libs.androidx.startup,
        libs.timber,
    )
}
