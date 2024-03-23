import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = libs.versions.android.target.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()

        multiDexEnabled = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true

            proguardFile(
                getDefaultProguardFile("proguard-android-optimize.txt"),
            )
        }
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

    namespace = "org.timemates.app"
}

kotlin {
    jvmToolchain(19)
}

dependencies {
    implementation(libs.koin.android)
    implementation(libs.androidx.appcompat)

    implementation(libs.android.multidex)

    implementation(libs.sqldelight.android.driver)

    implementation(projects.core.ui)

    implementation(projects.feature.users.data.database)
    implementation(projects.feature.authorization.data.database)

    implementation(projects.feature.timers.data.database)
    implementation(projects.feature.timers.data.database)

    implementation(libs.compose.accompanist.systemUiController)

    implementation(libs.androidx.compose.activity)

    implementation(projects.platform.common)
    implementation(libs.androidx.security.crypto.ktx)

    implementation(libs.essenty.instanceKeeper)
    implementation(libs.essenty.lifecycle)

    implementation(libs.androidx.lifecycle)
}