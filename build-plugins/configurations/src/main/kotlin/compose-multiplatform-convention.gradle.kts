plugins {
    id("multiplatform-library-convention")
    id("org.jetbrains.compose")
    id("com.android.library")
}

kotlin {
    jvmToolchain(17)
}

android {
    compileSdk = 34

    defaultConfig {
        namespace = "io.timemates.app.ui"
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    commonMainApi(compose.ui)
    commonMainApi(compose.foundation)
    commonMainApi(compose.runtime)
    commonMainApi(compose.material3)
    commonMainApi(compose.materialIconsExtended)
    commonMainApi(compose.uiTooling)
}

