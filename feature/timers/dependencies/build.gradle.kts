plugins {
    id(libs.plugins.configurations.koin.annotations.get().pluginId)
}

kotlin {
    jvm {
        jvmToolchain(11)
    }
    androidTarget()

    sourceSets {
        jvmMain {
            kotlin.srcDir("build/generated/ksp/jvm/jvmMain/kotlin")
        }
    }
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines)
    commonMainImplementation(libs.timemates.sdk)

    commonMainImplementation(projects.feature.timers.domain)
    commonMainImplementation(projects.feature.timers.data)
    commonMainImplementation(projects.feature.timers.presentation)
    commonMainImplementation(projects.core.ui)
}