plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
    alias(libs.plugins.kotlin.parcelize)
}

dependencies {
    commonMainApi(projects.styleSystem)
    commonMainApi(projects.foundation.mvi)
    commonMainApi(projects.foundation.mvi.koinCompose)
    commonMainApi(projects.feature.common.domain)

    commonMainImplementation(libs.kotlinx.coroutines)

    commonMainApi(compose.materialIconsExtended)

    commonMainApi(libs.koin.core)

    commonMainApi(projects.localization)
    commonMainApi(projects.localization.compose)

    commonMainApi(libs.decompose)
    commonMainApi(libs.decompose.jetbrains.compose)
}

android {
    namespace = "io.timemates.app.core.ui"
}