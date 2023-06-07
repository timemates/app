plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
}

dependencies {
    commonMainImplementation(libs.timemates.sdk)
    commonMainImplementation(libs.kotlinx.coroutines)
}