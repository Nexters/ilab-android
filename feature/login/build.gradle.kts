@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.feature)
}

android {
    namespace = "com.nexters.ilab.android.feature.login"
}

dependencies {
    implementations(
        libs.androidx.core,
        libs.androidx.activity.compose,
        libs.timber,
        libs.kakao.auth,
        libs.system.ui.controller,
    )
}
