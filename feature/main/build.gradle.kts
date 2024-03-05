@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.android.feature)
    alias(libs.plugins.ilab.android.retrofit)
    alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.nexters.ilab.android.feature.main"
}

dependencies {
    implementations(
        projects.feature.createimage,
        projects.feature.home,
        projects.feature.myalbum,
        projects.feature.mypage,
        projects.feature.setting,
        projects.feature.uploadphoto,
        projects.feature.privacypolicy,

        libs.androidx.core,
        libs.androidx.activity.compose,
        libs.kotlinx.collections.immutable,
        libs.system.ui.controller,
        libs.bundles.coil,
    )
}
