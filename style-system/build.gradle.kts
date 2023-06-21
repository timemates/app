plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.moko.multiplatform.resources)
}

kotlin {
    jvm()
    android()
}

dependencies {
    commonMainImplementation(compose.ui)
    commonMainImplementation(compose.material)
    commonMainImplementation(compose.material3)
    commonMainImplementation(compose.animation)
    commonMainImplementation(compose.foundation)
    commonMainImplementation(compose.materialIconsExtended)
    commonMainImplementation(compose.preview)

    commonMainImplementation(libs.moko.resources)
    commonMainImplementation(libs.moko.resources.compose)
}

android {
    compileSdk = libs.versions.android.target.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
    }

    namespace = "io.timemates.app.style.system"
}

multiplatformResources {
    multiplatformResourcesPackage = "io.timemates.app.style.system"
    multiplatformResourcesClassName = "Resources"
}