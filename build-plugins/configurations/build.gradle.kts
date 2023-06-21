plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    api(libs.kotlin.plugin)
    api(libs.android.gradle.plugin)
    api(libs.compose.multiplatform.plugin)
    api(libs.ksp.plugin)
    api(libs.koin.core)
    api(libs.koin.annotations)
}