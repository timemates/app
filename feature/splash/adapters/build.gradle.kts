plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
}

dependencies {
    commonMainImplementation(projects.feature.splash.domain)
    commonMainImplementation(projects.feature.authorization.domain)
}