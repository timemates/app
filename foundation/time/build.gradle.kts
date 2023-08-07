plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
}

kotlin {
    sourceSets {
        val jvmMain by getting

        val androidMain by getting {
            dependsOn(jvmMain)
        }
    }
}

dependencies {
    commonMainApi(libs.kotlinx.datetime)
}