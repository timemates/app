plugins {
    id(Deps.Plugins.Kotlin.Android)
    id(Deps.Plugins.Android.Application)
}

android {
    compileSdk = Deps.compileSdkVersion

    defaultConfig {
        applicationId = "com.y9vad9.pomodoro.client.android"
        minSdk = Deps.minSdkVersion
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = "1.7.21"
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Deps.Libs.Androidx.AppCompat)
    implementation(project(Deps.Modules.ViewModels))
    implementation(project(Deps.Modules.UseCases))
    implementation(project(Deps.Modules.Adapters.Repositories))
    implementation(project(Deps.Modules.Domain))
    implementation(Deps.Libs.Androidx.Compose.UI)
    implementation(Deps.Libs.Androidx.Compose.Icons)
    implementation(Deps.Libs.Androidx.Compose.ExtendedIcons)
    implementation(Deps.Libs.Androidx.Compose.Foundation)
    implementation(Deps.Libs.Androidx.Compose.JUnitTests)
    implementation(Deps.Libs.Androidx.Compose.Material3)
    implementation(Deps.Libs.Androidx.Compose.UITooling)
    implementation(Deps.Libs.Androidx.Compose.ViewModel)
    implementation(Deps.Libs.Androidx.Compose.Activity)
    implementation(Deps.Libs.Androidx.Compose.Navigation)
}