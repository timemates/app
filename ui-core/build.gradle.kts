plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    alias(libs.plugins.kotlin.parcelize)
}

dependencies {
    commonMainImplementation(projects.styleSystem)
    commonMainApi(projects.foundation.mvi)
    commonMainImplementation(projects.foundation.mvi.koinCompose)
    commonMainImplementation(projects.core)

    commonMainImplementation(libs.kotlinx.coroutines)

    commonMainApi(compose.materialIconsExtended)
    commonMainApi(compose.uiTooling)

    commonMainApi(libs.koin.core)

    commonMainApi(libs.decompose)
    commonMainApi(libs.decompose.jetbrains.compose)
}

android {
    compileSdk = libs.versions.android.target.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
    }

    namespace = "io.timemates.app.core.ui"
}