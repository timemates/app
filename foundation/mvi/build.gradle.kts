plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    jvm()

    explicitApi()
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines)
    commonMainApi(libs.decompose)
}