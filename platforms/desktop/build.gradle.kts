plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    application
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.runtime)

    implementation(projects.uiCore)

    //implementation(libs.sqldelight.jvm.driver)

    implementation(libs.timemates.engine.grpc)
    implementation(projects.feature.authorization.dependencies)
    implementation(projects.feature.authorization.data.database)
    implementation(projects.navigation)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}