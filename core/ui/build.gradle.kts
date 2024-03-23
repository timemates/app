plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    commonMainApi(projects.core.styleSystem)

    commonMainApi(projects.core.localization)
    commonMainApi(projects.core.localization.compose)

    commonMainApi(libs.timemates.sdk)
    commonMainApi(libs.bundles.presentation)

    commonMainApi(projects.foundation.time)
}

android {
    namespace = "org.timemates.app.core.ui"
}