@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.library)
    alias(libs.plugins.ilab.android.library.compose)
}

android {
    namespace = "com.nexters.ilab.android.core.designsystem"
}

dependencies {
    implementations(
        libs.androidx.core,
        libs.coil.compose,
    )
}
