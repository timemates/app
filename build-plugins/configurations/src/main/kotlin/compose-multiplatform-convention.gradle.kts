plugins {
    id("multiplatform-library-convention")
    id("org.jetbrains.compose")
}

android {
    defaultConfig {
        namespace = "io.timemates.app.ui"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    commonMainApi(compose.ui)
    commonMainApi(compose.foundation)
    commonMainImplementation(compose.runtime)
    commonMainApi(compose.material3)
    commonMainApi(compose.materialIconsExtended)
}

