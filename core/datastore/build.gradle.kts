@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.library)
    alias(libs.plugins.ilab.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.nexters.ilab.android.core.datastore"
}

dependencies {
    implementations(
        libs.androidx.core,
        libs.androidx.datastore.preferences,
    )
}
