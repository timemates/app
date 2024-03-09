plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    commonMainImplementation(projects.core.ui)
    commonMainImplementation(libs.timemates.sdk)
    commonMainApi(projects.foundation.mvi)
    commonMainImplementation(projects.core.styleSystem)
    commonMainImplementation(projects.feature.timers.domain)

    commonTestImplementation(projects.foundation.random)

    commonMainImplementation(libs.moko.resources.compose)
    commonMainImplementation(libs.moko.resources)

    commonMainImplementation(libs.kotlinx.datetime)
    commonMainImplementation(projects.foundation.time)
}

