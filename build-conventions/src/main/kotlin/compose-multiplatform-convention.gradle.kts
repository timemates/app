plugins {
    id("multiplatform-library-convention")
    id("org.jetbrains.compose")
}

android {
    defaultConfig {
        namespace = "org.timemates.app.ui"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
}

dependencies {
    commonMainApi(compose.ui)
    commonMainApi(compose.foundation)
    commonMainImplementation(compose.runtime)
    commonMainApi(compose.material3)
    commonMainApi(compose.materialIconsExtended)
}

