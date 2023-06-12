plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                //implementation(libs.timemates.engine.grpc)
            }
        }
    }
}

dependencies {
    commonMainApi(libs.timemates.sdk)
}