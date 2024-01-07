plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
}

kotlin {
    jvm()
    androidTarget()

    sourceSets {
        androidMain {
            dependencies {
                api(libs.androidx.lifecycle)
            }
        }
    }
}

android {
    compileSdk = libs.versions.android.target.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
    }

    namespace = "io.timemates.androidx.viewmodel"
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines)
}