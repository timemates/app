plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
}

dependencies {
    commonMainApi(libs.timemates.sdk)
    commonMainImplementation(libs.kotlinx.coroutines)
}