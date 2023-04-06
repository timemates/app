plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
}

kotlin {
    jvm()
}

dependencies {
    commonMainApi(compose.ui)
    commonMainApi(compose.material)
    commonMainApi(compose.material3)
    commonMainApi(compose.animation)
    commonMainApi(compose.foundation)
    commonMainApi(compose.uiTooling)
    commonMainApi(compose.compiler.auto)
    commonMainApi(compose.preview)
    commonMainImplementation(libs.kamel.image)
}