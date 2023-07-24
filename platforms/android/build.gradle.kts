plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = libs.versions.android.target.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
        targetSdk = libs.versions.android.target.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    packaging {
        resources {
            excludes.add("META-INF/INDEX.LIST")
            excludes.add("META-INF/io.netty.versions.properties")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.jetpackComposeCompilerVersion.get()
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
    implementation(libs.koin.core)
    implementation(libs.androidx.appcompat)

    implementation(libs.sqldelight.android.driver)

    implementation(projects.feature.authorization.dependencies)
    implementation(projects.feature.authorization.data.database)

    implementation(libs.grpc.okhttp)

    implementation(libs.androidx.compose.activity)
    implementation(projects.navigation)
    implementation(projects.styleSystem)
}