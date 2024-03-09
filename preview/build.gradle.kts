plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "io.timemates.preview.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.jetpackComposeCompilerVersion.get()
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.timemates.sdk)
    implementation(libs.kotlinx.datetime)

    implementation(projects.core.styleSystem)

    debugImplementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.compose.preview)
    implementation(libs.androidx.compose.material3)

    implementation(projects.core.localization)
    implementation(projects.core.localization.compose)

    implementation(libs.decompose)
    implementation(libs.decompose.jetbrains.compose)

    implementation(projects.feature.authorization.presentation)
    implementation(projects.feature.timers.presentation)
}
