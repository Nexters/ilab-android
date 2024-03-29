@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.library)
    alias(libs.plugins.ilab.android.library.compose)
    alias(libs.plugins.ilab.android.hilt)
    alias(libs.plugins.ilab.android.retrofit)
    id("kotlin-parcelize")
}

android {
    namespace = "com.nexters.ilab.android.core.common"
}

dependencies {
    implementations(
        libs.androidx.core,
        libs.androidx.hilt.navigation.compose,

        libs.timber,
        libs.bundles.androidx.lifecycle,
    )
}
