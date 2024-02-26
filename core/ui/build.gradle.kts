@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.library)
    alias(libs.plugins.ilab.android.library.compose)
}

android {
    namespace = "com.nexters.ilab.android.core.ui"
}

dependencies {
    implementations(
        projects.core.designsystem,
        projects.core.common,

        libs.bundles.coil,
    )
}
