plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
}

kotlin {
    jvm()
    androidTarget()

    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.androidx.compose.viewmodel)
                implementation(libs.koin.androidx.compose)
            }
        }
    }
}

dependencies {
    commonMainImplementation(compose.ui)
    commonMainImplementation(compose.runtime)
    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.koin.compose)

    commonMainImplementation(projects.foundation.mvi)
}

android {
    namespace = "org.timemates.app.mvi.compose"
}