@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.feature)
}

android {
    namespace = "com.nexters.ilab.android.feature.privacypolicy"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            "String",
            "PRIVACY_POLICY_WEB_VIEW_URL",
            properties["PRIVACY_POLICY_WEB_VIEW_URL"] as String,
        )
    }
}

dependencies {
    implementations(
        libs.androidx.core,
        libs.timber,
        libs.system.ui.controller,
        libs.accompanist.webview,
    )
}
