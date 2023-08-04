plugins {
    `kotlin-dsl`
}

group = "publish-library"
version = "SNAPSHOT"

kotlin {
    jvmToolchain(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    plugins.register("publish-library") {
        id = "publish-library"
        implementationClass = "com.y9vad9.maven.publish.DeployPlugin"
    }
}

dependencies {
    implementation("gradle.plugin.com.github.jengelman.gradle.plugins:shadow:7.0.0")
}