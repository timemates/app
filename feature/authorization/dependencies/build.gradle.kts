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

    commonMainImplementation(projects.uiCore)
    commonMainImplementation(projects.feature.authorization.domain)
    commonMainImplementation(projects.feature.authorization.presentation)
    commonMainImplementation(projects.feature.authorization.data)
    commonMainImplementation(projects.feature.authorization.data.database)
    commonMainImplementation(projects.core)
}