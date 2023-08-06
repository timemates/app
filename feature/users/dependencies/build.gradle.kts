plugins {
    id(libs.plugins.configurations.koin.annotations.get().pluginId)
}

kotlin {
    jvm {
        jvmToolchain(11)
    }
    android()

    sourceSets {
        val jvmMain by getting {
            kotlin.srcDir("build/generated/ksp/jvm/jvmMain/kotlin")
        }
    }
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines)
    commonMainImplementation(libs.timemates.sdk)

    commonMainImplementation(projects.feature.users.data)

    commonMainImplementation(projects.foundation.time)

    commonMainImplementation(projects.feature.users.domain)
    commonMainImplementation(projects.feature.users.data.database)
    commonMainImplementation(projects.feature.users.presentation)
    commonMainImplementation(projects.feature.common.domain)
}