plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    alias(libs.plugins.kotlin.parcelize)
}

dependencies {
    commonMainImplementation(libs.decompose)
    commonMainImplementation(libs.decompose.jetbrains.compose)
    commonMainImplementation(libs.koin.core)

    commonMainImplementation(projects.styleSystem)
    commonMainImplementation(projects.foundation.mvi)
    commonMainImplementation(projects.foundation.mvi.koinCompose)
    commonMainImplementation(projects.core)

    commonMainImplementation(libs.kotlinx.coroutines)

    commonMainImplementation(projects.feature.authorization.presentation)
}