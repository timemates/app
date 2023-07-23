plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    alias(libs.plugins.moko.multiplatform.resources)
}

kotlin {
    jvm()
    android()
}

dependencies {
    commonMainApi(compose.ui)
    commonMainImplementation(compose.material)
    commonMainApi(compose.material3)
    commonMainApi(compose.animation)
    commonMainApi(compose.foundation)
    commonMainApi(compose.materialIconsExtended)

    commonMainImplementation(libs.moko.resources)
    commonMainImplementation(libs.moko.resources.compose)
}

android {
    namespace = "io.timemates.app.style.system"
}

multiplatformResources {
    multiplatformResourcesPackage = "io.timemates.app.style.system"
    multiplatformResourcesClassName = "Resources"
}