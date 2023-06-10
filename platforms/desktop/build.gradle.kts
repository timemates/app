plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
}

kotlin {
    jvm()
}

dependencies {
    commonMainImplementation(compose.desktop.currentOs)
    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.compiler.auto)
}