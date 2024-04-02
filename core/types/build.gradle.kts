plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
}

dependencies {
    commonMainApi(libs.timemates.sdk)
    commonMainApi(libs.kotlinx.datetime)
}
