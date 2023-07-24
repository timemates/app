plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
}

dependencies {
    commonMainImplementation(projects.feature.common.presentation)
    commonMainImplementation(libs.timemates.sdk)
    commonMainImplementation(projects.foundation.mvi)
    commonMainImplementation(projects.styleSystem)
    commonMainImplementation(projects.feature.timers.domain)

    commonTestImplementation(projects.foundation.random)
}

