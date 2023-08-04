plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.graalvm.native.image)
    application
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.runtime)

    implementation(libs.koin.core)

    implementation(libs.sqldelight.runtime.jvm)
    implementation(libs.sqldelight.jvm.driver)

    implementation(projects.feature.common.domain)

    implementation(libs.timemates.sdk)
    implementation(libs.timemates.engine.grpc)
    implementation(projects.feature.authorization.dependencies)
    implementation(projects.feature.authorization.data.database)
    implementation(projects.feature.users.data.database)
    implementation(projects.feature.users.dependencies)
    implementation(projects.feature.timers.data.database)
    implementation(projects.feature.timers.dependencies)
    implementation(projects.navigation)

    implementation(libs.tink)

    implementation(projects.foundation.random)
    implementation(projects.foundation.secureCredentials)

    implementation(projects.localization)
    implementation(projects.localization.compose)

    implementation(libs.graalvm.svm.image)
    implementation(libs.graalvm.xerial.sqlite)

    implementation(projects.styleSystem)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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

graalvmNative {
    toolchainDetection.set(true)

    binaries {
        named("main") {
            imageName.set("application")
            mainClass.set("io.timemates.app.MainKt")

            jvmArgs(
                "--add-exports=org.graalvm.nativeimage.builder/com.oracle.svm.core=ALL-UNNAMED",
                "--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED"
            )

            buildArgs(
                "--add-exports=org.graalvm.nativeimage.builder/com.oracle.svm.core=ALL-UNNAMED",
                "--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED"
            )

            useFatJar.set(true)
        }
    }
}