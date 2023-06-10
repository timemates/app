plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()

    explicitApi()
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines)
}