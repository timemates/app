plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
}

dependencies {
    commonMainImplementation(libs.koin.core)

    commonMainImplementation(libs.timemates.sdk)
    commonMainImplementation(libs.timemates.engine.rsocket)

    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.koin.compose)

    commonMainImplementation(libs.ktor.client.cio)

    commonMainImplementation(projects.feature.system.dependencies)

    commonMainImplementation(libs.kotlinx.coroutines)

    commonMainImplementation(libs.sqldelight.runtime)

    commonMainApi(projects.foundation.time)
    commonMainImplementation(projects.feature.common.domain)

    commonMainImplementation(projects.feature.authorization.dependencies)
    commonMainImplementation(projects.feature.timers.dependencies)
    commonMainImplementation(projects.feature.users.dependencies)

    commonMainImplementation(projects.feature.authorization.data.database)

    jvmMainApi(libs.sqldelight.jvm.driver)
    androidMainApi(libs.sqldelight.android.driver)

    commonMainImplementation(projects.navigation)
    commonMainImplementation(projects.styleSystem)

    commonMainImplementation(projects.foundation.mvi)
    commonMainImplementation(projects.foundation.mvi.koinCompose)

    commonMainImplementation(projects.feature.authorization.domain)
}