plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    id(libs.plugins.configurations.unit.tests.mockable.get().pluginId)
    alias(libs.plugins.kotlin.parcelize)
}

dependencies {
    commonMainApi(projects.core.styleSystem)
    commonMainApi(projects.foundation.mvi)
    commonMainApi(projects.foundation.mvi.koinCompose)

    commonMainImplementation(libs.kotlinx.coroutines)

    commonMainApi(compose.materialIconsExtended)

    commonMainApi(libs.koin.core)

    commonMainApi(projects.core.localization)
    commonMainApi(projects.core.localization.compose)

    commonMainApi(libs.decompose)
    commonMainApi(libs.decompose.jetbrains.compose)

    commonMainApi(projects.core.types.serializable)
    commonMainApi(libs.kotlinx.immutable)

    commonMainImplementation(libs.ktor.client.core)

    commonMainImplementation(projects.foundation.time)

    commonMainApi(libs.flowmvi.core)
    commonMainApi(libs.flowmvi.compose)
    commonMainApi(libs.flowmvi.savedstate)
    commonMainApi(libs.flowmvi.essenty)
    commonMainApi(libs.flowmvi.essenty.compose)
}

android {
    namespace = "org.timemates.app.core.ui"
}