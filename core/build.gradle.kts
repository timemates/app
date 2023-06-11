plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.koin.configuration)
}

kotlin {
    jvm()
}

dependencies {
    commonMainApi(libs.timemates.sdk)
    commonMainImplementation(libs.kotlinx.coroutines)
}