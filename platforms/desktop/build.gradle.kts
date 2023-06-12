plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    application
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.runtime)

    implementation(projects.presentation)
}