plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
}

dependencies {
    commonMainApi(libs.timemates.sdk)
    commonMainApi(projects.foundation.validation)
    commonMainImplementation(libs.kotlinx.coroutines)

    commonTestImplementation(projects.foundation.random)
}