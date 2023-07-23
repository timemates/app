plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
}

dependencies {
    commonMainApi(libs.timemates.sdk)
    commonMainImplementation(libs.kotlinx.coroutines)
}

android {
    namespace = "io.timemates.app.core"
}