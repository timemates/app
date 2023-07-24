plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines)
    commonMainImplementation(compose.ui)
}

android {
    namespace = "io.timemates.app.core.ui"
}