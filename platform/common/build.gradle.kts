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

    commonMainImplementation(projects.feature.splash.dependencies)

    commonMainImplementation(libs.kotlinx.coroutines)

    commonMainImplementation(libs.sqldelight.runtime)

    commonMainApi(projects.foundation.time)
    commonMainImplementation(projects.core.ui)

    commonMainImplementation(projects.feature.authorization.dependencies)
    commonMainImplementation(projects.feature.timers.dependencies)
    commonMainImplementation(projects.feature.users.dependencies)

    commonMainImplementation(projects.feature.authorization.data.database)

    jvmMainApi(libs.sqldelight.jvm.driver)
    androidMainApi(libs.sqldelight.android.driver)

    commonMainApi(projects.core.navigation)
    commonMainApi(projects.core.styleSystem)

    commonMainApi(libs.timemates.credentials.manager)

    commonMainImplementation(projects.foundation.mvi)
    commonMainImplementation(projects.foundation.mvi.koinCompose)

    commonMainImplementation(projects.feature.authorization.domain)
}