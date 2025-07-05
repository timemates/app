package app.timemate.client.build.conventions

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("app.timemate.client.build.conventions.multiplatform-convention")
}

val libs = the<LibrariesForLibs>()

dependencies {
    commonTestImplementation(libs.kotlinx.coroutines.test)
    commonTestImplementation(libs.kotlin.test)
    "jvmMainImplementation"(libs.mockk)
}

tasks.withType<Test> {
    useJUnitPlatform()
}