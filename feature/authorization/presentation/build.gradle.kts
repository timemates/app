plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    commonMainImplementation(projects.core.ui)
    commonMainImplementation(libs.timemates.sdk)
    commonMainImplementation(projects.feature.authorization.domain)

    commonMainImplementation(projects.core.styleSystem)

    commonTestImplementation(projects.foundation.random)
}

