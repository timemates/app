plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
}

dependencies {
    commonMainImplementation(projects.feature.system.domain)
    commonMainImplementation(projects.feature.authorization.domain)
}