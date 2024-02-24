@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.feature)
    alias(libs.plugins.ilab.android.retrofit)
}

android {
    namespace = "com.nexters.ilab.android.feature.uploadphoto"
}

dependencies {
    implementations(
        libs.kotlinx.collections.immutable,
        libs.androidx.core,
        libs.timber,
        libs.system.ui.controller,
    )
}
