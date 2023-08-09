plugins {
    id("multiplatform-library-convention")
    id("org.jetbrains.compose")
    id("com.android.library")
}

kotlin {
    jvmToolchain(19)
}

android {
    compileSdk = 34

    defaultConfig {
        namespace = "io.timemates.app.ui"
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_19
        sourceCompatibility = JavaVersion.VERSION_19
    }
}

dependencies {
    commonMainImplementation(compose.ui)
    commonMainImplementation(compose.foundation)
    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.material3)
    commonMainImplementation(compose.materialIconsExtended)
    commonMainImplementation(compose.uiTooling)
}

