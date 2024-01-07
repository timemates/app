plugins {
    id(libs.plugins.configurations.compose.multiplatform.get().pluginId)
}

kotlin {
    sourceSets {
        androidMain {
            dependsOn(jvmMain.get())
        }
    }
}

dependencies {
    commonMainApi(libs.kotlinx.datetime)
}