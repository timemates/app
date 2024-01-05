plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    application
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.runtime)

    implementation(libs.koin.core)

    implementation(projects.feature.common.domain)

    implementation(libs.timemates.sdk)
    implementation(libs.timemates.engine.rsocket)

    implementation(libs.ktor.client.cio)
    
    implementation(projects.feature.users.data.database)
    implementation(projects.feature.authorization.dependencies)
    implementation(projects.feature.authorization.data.database)

    implementation(projects.feature.timers.data.database)
    implementation(projects.feature.timers.dependencies)

    implementation(projects.navigation)

    implementation(projects.foundation.time)

    implementation(projects.styleSystem)

    implementation(projects.platforms.common)
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