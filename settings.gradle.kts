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

    ":core:data",
    ":core:designsystem",
    ":core:domain",
    ":core:network",
    ":core:datastore",
    ":core:ui",
    ":core:util",

    ":feature:camera",
    ":feature:home",
    ":feature:login",
    ":feature:mypage",
)
