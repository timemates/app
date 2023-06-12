plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    jvm()
    android()
}

dependencies {
    commonMainImplementation(compose.ui)
    commonMainImplementation(compose.runtime)

    commonMainImplementation(projects.features.mvi)
}

android {
    compileSdk = libs.versions.android.target.get().toInt()
}