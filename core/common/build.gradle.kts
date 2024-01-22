@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.library)
}

android {
    namespace = "com.nexters.ilab.android.core.common"
}

dependencies {
    implementations(
        libs.kotlinx.datetime,
        libs.androidx.core,
    )
}
