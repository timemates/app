plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
    alias(libs.plugins.moko.multiplatform.resources)
}

kotlin {
    jvm()
    androidTarget()

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/moko/commonMain")
        }

        val androidMain by getting {
            kotlin.srcDir("build/generated/moko/androidMain")
        }

        val jvmMain by getting {
            kotlin.srcDir("build/generated/moko/jvmMain")
        }
    }
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