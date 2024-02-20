plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

version = "SNAPSHOT"

kotlin {
    jvm()
    androidTarget()

    jvmToolchain(19)
}

android {
    compileSdk = 34

    defaultConfig {
        namespace = "org.timemates.app"
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_19
        sourceCompatibility = JavaVersion.VERSION_19
    }
}

