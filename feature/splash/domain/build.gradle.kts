plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
}

dependencies {
    commonMainApi(libs.timemates.sdk)
    commonMainImplementation(libs.kotlinx.coroutines)
}

android {
    namespace = "org.timemates.app.core"
}