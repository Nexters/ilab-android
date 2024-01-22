@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.feature)
}

android {
    namespace = "com.nexters.ilab.android.feature.main"
}

dependencies {
    implementations(
        projects.feature.camera,
        projects.feature.home,
        projects.feature.mypage,
        projects.feature.setting,

        libs.androidx.core,
        libs.kotlinx.collections.immutable,
    )
}
