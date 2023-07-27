plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
}

dependencies {
    commonMainApi(libs.timemates.sdk)
    commonMainImplementation(libs.kotlinx.coroutines)
    commonMainApi(libs.kotlinx.datetime)

    commonTestImplementation(projects.foundation.random)
    commonMainImplementation(projects.foundation.stdlibExt)
    commonMainApi(projects.foundation.validation)
}