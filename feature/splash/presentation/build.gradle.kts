plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    commonMainApi(projects.core.styleSystem)
    commonMainApi(projects.feature.splash.domain)

    commonMainImplementation(libs.kotlinx.coroutines)

    commonMainApi(compose.materialIconsExtended)

    commonMainApi(libs.koin.core)

    commonMainImplementation(projects.core.ui)

    commonMainApi(projects.core.localization)
    commonMainApi(projects.core.localization.compose)

    commonMainApi(libs.decompose)
    commonMainApi(libs.decompose.jetbrains.compose)
}

android {
    namespace = "org.timemates.app.core.ui"
}