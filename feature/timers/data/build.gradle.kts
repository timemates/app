plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
}

dependencies {
    commonMainImplementation(projects.feature.timers.domain)
    commonMainImplementation(projects.feature.timers.data.database)

    commonMainImplementation(libs.timemates.sdk)
}