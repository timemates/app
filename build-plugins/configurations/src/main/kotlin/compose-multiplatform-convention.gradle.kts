plugins {
    id("multiplatform-library-convention")
    id("org.jetbrains.compose")
    id("com.android.library")
}

android {
    compileSdk = 33

    defaultConfig {
        namespace = "io.timemates.app.ui"
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

