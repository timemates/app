plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
}

dependencies {
    commonMainImplementation(projects.feature.users.domain)
    commonMainImplementation(projects.feature.users.data.database)
    commonMainImplementation(projects.feature.users.data.sdk)

    commonMainImplementation(projects.foundation.time)
    commonMainImplementation(projects.foundation.stdlibExt)
}