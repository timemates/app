plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
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

    commonMainCompileOnly(compose.desktop.currentOs)
}

android {
    namespace = "io.timemates.app.style.system"
}

multiplatformResources {
    multiplatformResourcesPackage = "io.timemates.app.style.system"
    multiplatformResourcesClassName = "Resources"
}