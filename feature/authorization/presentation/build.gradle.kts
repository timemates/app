plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
}

dependencies {
    commonMainImplementation(projects.feature.common.presentation)
    commonMainImplementation(libs.timemates.sdk)
    commonMainApi(projects.foundation.mvi)
    commonMainImplementation(projects.feature.authorization.domain)

    commonMainImplementation(projects.styleSystem)

    commonTestImplementation(projects.foundation.random)

    commonMainImplementation(libs.moko.resources.compose)
    commonMainImplementation(libs.moko.resources)
}

