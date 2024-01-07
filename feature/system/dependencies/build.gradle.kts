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

    commonMainImplementation(projects.feature.system.domain)
    commonMainImplementation(projects.feature.system.presentation)
    commonMainImplementation(projects.feature.system.adapters)

    commonMainImplementation(projects.feature.authorization.domain)

    commonMainImplementation(projects.feature.common.domain)
}