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

    commonMainImplementation(projects.feature.splash.domain)
    commonMainImplementation(projects.feature.splash.presentation)
    commonMainImplementation(projects.feature.splash.adapters)

    commonMainImplementation(projects.feature.authorization.domain)

    commonMainImplementation(projects.core.ui)
}