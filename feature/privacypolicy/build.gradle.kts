@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.feature)
}

android {
    namespace = "com.nexters.ilab.android.feature.privacypolicy"
}

dependencies {
    implementations(
        libs.androidx.core,
        libs.timber,
        libs.system.ui.controller,
        libs.accompanist.webview,
    )
}
