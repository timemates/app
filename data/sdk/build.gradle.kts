plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
}

dependencies {
    commonMainApi(libs.timemates.sdk)
    // commonMainApi(libs.timemates.engine.grpc)
}