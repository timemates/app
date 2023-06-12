plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

kotlin {
    jvm()
    android()
}

android {
    compileSdk = libs.versions.android.target.get().toInt()
}

dependencies {
    commonMainImplementation(projects.core)
    commonMainImplementation(projects.data)
    commonMainImplementation(projects.presentation)

    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.koin.annotations)

    commonMainImplementation(libs.kotlinx.coroutines)

    ksp(libs.koin.ksp.compiler)
}