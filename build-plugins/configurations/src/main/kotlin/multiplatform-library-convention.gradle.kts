plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

version = "SNAPSHOT"

kotlin {
    jvm()
    android()

    jvmToolchain(19)
}

android {
    compileSdk = 34

    defaultConfig {
        namespace = "io.timemates.app"
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_19
        sourceCompatibility = JavaVersion.VERSION_19
    }
}

