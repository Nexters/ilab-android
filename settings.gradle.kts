rootProject.name = "ilab-android"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":app",

    ":core:common",
    ":core:data",
    ":core:designsystem",
    ":core:domain",
    ":core:network",
    ":core:datastore",
    ":core:ui",

    ":feature:camera",
    ":feature:home",
    ":feature:intro",
    ":feature:login",
    ":feature:main",
    ":feature:mypage",
    ":feature:navigator",
    ":feature:setting",
)
