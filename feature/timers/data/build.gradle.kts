plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
}

dependencies {
    commonMainImplementation(projects.feature.timers.domain)
    commonMainImplementation(projects.feature.timers.data.database)

    commonMainImplementation(projects.foundation.smartValue)
    commonMainImplementation(projects.foundation.time)

    commonMainApi(libs.sqldelight.runtime)
    commonMainImplementation(libs.sqldelight.coroutines)
    commonMainImplementation(libs.sqldelight.paging)

    commonMainImplementation(libs.kotlinx.datetime)
    commonMainImplementation(libs.timemates.sdk)
}