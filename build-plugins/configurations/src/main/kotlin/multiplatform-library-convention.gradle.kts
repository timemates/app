plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

version = "SNAPSHOT"

kotlin {
    jvm {
        jvmToolchain(11)
    }

    android()
}

android {
    compileSdk = 33

    defaultConfig {
        namespace = "io.timemates.app"
    }
}

