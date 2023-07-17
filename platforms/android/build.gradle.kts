plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = libs.versions.android.target.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    packaging {
        resources {
            excludes.add("META-INF/*")
        }
    }

    namespace = "io.timemates.app"
}

kotlin {
    jvmToolchain(19)
}

dependencies {
    implementation(libs.timemates.sdk)
    implementation(libs.timemates.engine.grpc)
    implementation(libs.timemates.engine.grpc.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.activity)

    implementation(libs.sqldelight.android.driver)

    implementation(projects.feature.authorization.dependencies)
    implementation(projects.feature.authorization.data.database)

    implementation(projects.navigation)
    implementation(projects.uiCore)
}