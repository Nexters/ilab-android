@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.ilab.jvm.kotlin)
}

dependencies {
    implementations(
        libs.javax.inject,
    )
}
