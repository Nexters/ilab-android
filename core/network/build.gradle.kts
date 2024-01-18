@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.library)
    alias(libs.plugins.ilab.android.retrofit)
    alias(libs.plugins.ilab.android.hilt)
    alias(libs.plugins.google.secrets)
    id("kotlinx-serialization")
}

android {
    namespace = "com.nexters.ilab.android.core.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementations(
        projects.core.preference,

        libs.timber,
    )
}

secrets {
    defaultPropertiesFileName = "secrets.properties"
}
