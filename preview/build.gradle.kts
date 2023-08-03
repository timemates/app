import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.android
import org.gradle.kotlin.dsl.debugImplementation
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.libs
import org.gradle.kotlin.dsl.projects

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
    buildFeatures.compose = true
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(projects.styleSystem)

    debugImplementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.compose.preview)
    implementation(libs.androidx.compose.material3)

    implementation(projects.localization)
    implementation(projects.localization.compose)

    implementation(projects.feature.authorization.presentation)
    implementation(projects.styleSystem)
}