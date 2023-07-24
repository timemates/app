plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    application
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.runtime)

    implementation(libs.koin.core)

    implementation(libs.sqldelight.jvm.driver)

    implementation(libs.timemates.sdk)
    implementation(libs.timemates.engine.grpc)
    implementation(projects.feature.authorization.dependencies)
    implementation(projects.feature.authorization.data.database)
    implementation(projects.navigation)
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}