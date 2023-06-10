plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    jvm()
}

dependencies {
    commonMainImplementation(compose.ui)
    commonMainImplementation(compose.material)
    commonMainImplementation(compose.material3)
    commonMainImplementation(compose.animation)
    commonMainImplementation(compose.foundation)
    commonMainImplementation(compose.materialIconsExtended)
    commonMainImplementation(compose.preview)
}

android {
    compileSdk = 34
}