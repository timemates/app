plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
}

dependencies {
    commonMainImplementation(projects.core.ui)
    commonMainImplementation(libs.timemates.sdk)
    commonMainImplementation(projects.core.styleSystem)
    commonMainImplementation(projects.feature.users.domain)

    commonTestImplementation(projects.foundation.random)
}

