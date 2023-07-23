plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
}

kotlin {
    jvm()

    explicitApi()
}

dependencies {
    commonMainApi(projects.foundation.viewmodel)
    commonMainImplementation(libs.kotlinx.coroutines)
}