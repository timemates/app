plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
}

kotlin {
    explicitApi()
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines)
}