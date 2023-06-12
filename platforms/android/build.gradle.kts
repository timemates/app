plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = libs.versions.android.target.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.activity)

    implementation(projects.presentation)
    implementation(projects.sharedDeps)
}