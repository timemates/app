plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.parcelize)
}

kotlin {
    jvm()
    android()
}

dependencies {
    commonMainImplementation(projects.styleSystem)
    commonMainImplementation(projects.features.mvi)
    commonMainImplementation(projects.core)

    commonMainImplementation(libs.kotlinx.coroutines)

    commonMainApi(compose.ui)
    commonMainApi(compose.foundation)
    commonMainApi(compose.runtime)
    commonMainApi(compose.material3)
    commonMainApi(compose.materialIconsExtended)
    commonMainApi(compose.uiTooling)

    commonMainApi(libs.koin.core)

    commonMainApi(libs.decompose)
    commonMainApi(libs.decompose.jetbrains.compose)
}

android {
    compileSdk = libs.versions.android.target.get().toInt()
}