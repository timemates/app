plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    commonMainApi(libs.timemates.sdk)
    commonMainApi(libs.kotlinx.serialization)

    commonMainApi(libs.kotlinx.datetime)
}
