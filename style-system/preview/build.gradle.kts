plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    alias(libs.plugins.moko.multiplatform.resources)
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