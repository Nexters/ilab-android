@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.library)
    alias(libs.plugins.ilab.android.hilt)
    alias(libs.plugins.ilab.android.retrofit)
    id("kotlinx-serialization")
}

android {
    namespace = "com.nexters.ilab.android.core.data"
}

dependencies {
    implementations(
        projects.core.network,
        projects.core.datastore,
        projects.core.domain,

        libs.timber,
        libs.bundles.coil,
    )
}
