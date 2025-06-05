plugins {
    `kotlin-dsl`
}

group = "build-conventions"
version = "SNAPSHOT"

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api(libs.classpath.kotlin.plugin)
    api(libs.classpath.sqldelight.plugin)
    api(libs.classpath.compose.plugin)
    api(libs.classpath.compose.compiler.plugin)
    api(libs.classpath.ksp.plugin)
    api(files((libs).javaClass.superclass.protectionDomain.codeSource.location))
}