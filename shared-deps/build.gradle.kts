plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.koin.configuration)
}

kotlin {
    jvm()
    android()
}

android {
    compileSdk = libs.versions.android.target.get().toInt()
}