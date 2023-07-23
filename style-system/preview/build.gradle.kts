plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
}

kotlin {
    jvm()
}

dependencies {
    commonMainImplementation(projects.styleSystem)
    commonMainImplementation(compose.desktop.common)
    commonMainImplementation(compose.uiTooling)
    commonMainImplementation(compose.preview)
}