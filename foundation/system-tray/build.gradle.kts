plugins {
    id(libs.plugins.configurations.jvm.library.get().pluginId)
}

dependencies {
    implementation(libs.jna.core)
    implementation(libs.jna.platform)
}