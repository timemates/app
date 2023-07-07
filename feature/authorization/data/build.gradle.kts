plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
}

dependencies {
    commonMainImplementation(projects.feature.authorization.domain)
    commonMainImplementation(projects.feature.authorization.data.database)
    commonMainImplementation(projects.feature.authorization.data.sdk)
}