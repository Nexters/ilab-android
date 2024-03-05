@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.feature)
    alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.nexters.ilab.android.feature.home"
}

dependencies {
    implementations(
        libs.androidx.core,
        libs.timber,
    )
}
