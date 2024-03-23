plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

kotlin {
    jvmToolchain(19)
}

dependencies {
    api(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    api(libs.kotlin.plugin)
    api(libs.android.gradle.plugin)
    api(libs.compose.multiplatform.plugin)
    api(libs.ksp.plugin)
    api(libs.koin.core)
    api(libs.koin.annotations)
}