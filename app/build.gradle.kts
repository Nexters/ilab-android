@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import java.util.Properties

plugins {
    alias(libs.plugins.ilab.android.application)
    alias(libs.plugins.ilab.android.application.compose)
    alias(libs.plugins.ilab.android.application.firebase)
    alias(libs.plugins.ilab.android.hilt)
    alias(libs.plugins.google.secrets)
}

android {
    namespace = "com.nexters.ilab.android"

    signingConfigs {
        create("release") {
            val propertiesFile = rootProject.file("keystore.properties")
            val properties = Properties()
            properties.load(propertiesFile.inputStream())
            storeFile = file(properties["STORE_FILE"] as String)
            storePassword = properties["STORE_PASSWORD"] as String
            keyAlias = properties["KEY_ALIAS"] as String
            keyPassword = properties["KEY_PASSWORD"] as String
        }
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".dev"
            manifestPlaceholders += mapOf(
                "appName" to "@string/app_name_dev",
            )
        }

        getByName("release") {
            isDebuggable = false
            signingConfig = signingConfigs.getByName("debug")
            manifestPlaceholders += mapOf(
                "appName" to "@string/app_name",
            )
        }
    }
}

dependencies {
    implementations(
        projects.core.common,
        projects.core.data,
        projects.core.designsystem,
        projects.core.domain,
        projects.core.network,
        projects.core.datastore,
        projects.core.ui,

        projects.feature.createimage,
        projects.feature.home,
        projects.feature.intro,
        projects.feature.login,
        projects.feature.main,
        projects.feature.navigator,
        projects.feature.mypage,
        projects.feature.setting,
        projects.feature.uploadphoto,

        libs.androidx.activity.compose,
        libs.androidx.core,
        libs.androidx.startup,
        libs.timber,
        libs.kakao.auth,
    )
}

secrets {
    defaultPropertiesFileName = "secrets.properties"
}
