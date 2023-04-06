plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
}

kotlin {
    jvm()
}

dependencies {
    commonMainImplementation(projects.uiSystem)
}