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

    implementation(projects.feature.common.domain)

    implementation(libs.timemates.sdk)
    implementation(libs.timemates.engine.grpc)
    
    implementation(projects.feature.users.data.database)
    implementation(projects.feature.authorization.dependencies)
    implementation(projects.feature.authorization.data.database)

    implementation(projects.navigation)


    implementation(projects.foundation.time)

    implementation(projects.styleSystem)
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

tasks.withType<Zip> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<Tar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}