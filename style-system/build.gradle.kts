plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    alias(libs.plugins.libres)
}

dependencies {
    commonMainApi(libs.libres.compose)
}

android {
    namespace = "io.timemates.app.style.system"
}

libres {
    generatedClassName = "Resources"
}