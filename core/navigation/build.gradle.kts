plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    commonMainImplementation(libs.timemates.sdk)
    commonMainApi(libs.decompose)
    commonMainApi(libs.decompose.jetbrains.compose)
    commonMainImplementation(libs.koin.core)

    commonMainImplementation(projects.core.styleSystem)
    commonMainImplementation(projects.foundation.mvi)
    commonMainImplementation(projects.foundation.mvi.koinCompose)

    commonMainImplementation(libs.kotlinx.coroutines)

    commonMainImplementation(projects.feature.authorization.presentation)
    commonMainImplementation(projects.feature.timers.presentation)

    commonMainImplementation(projects.feature.splash.domain)
    commonMainImplementation(projects.feature.splash.presentation)

    commonMainImplementation(libs.kotlinx.serialization)
}