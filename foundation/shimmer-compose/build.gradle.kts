plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines)
    commonMainImplementation(compose.ui)
}

android {
    namespace = "org.timemates.app.core.ui"
}