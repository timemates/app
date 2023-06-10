plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.koin.configuration)
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

    commonMainImplementation(compose.ui)
    commonMainImplementation(compose.foundation)
    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.material3)
    commonMainImplementation(compose.materialIconsExtended)
    commonMainImplementation(compose.uiTooling)
}

android {
    compileSdk = 34
}