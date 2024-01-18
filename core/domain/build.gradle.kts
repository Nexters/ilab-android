@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.library)
}

android {
    namespace = "com.nexters.ilab.android.core.domain"
}

dependencies {
    implementations(
        projects.core.data,

        libs.javax.inject,
    )
}
