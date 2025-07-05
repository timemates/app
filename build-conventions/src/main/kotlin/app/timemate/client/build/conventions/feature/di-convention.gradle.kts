package app.timemate.client.build.conventions.feature

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("app.cash.sqldelight")
    id("app.timemate.client.build.conventions.detekt-convention")
    id("app.timemate.client.build.conventions.multiplatform-convention")
    id("com.google.devtools.ksp")
}

val libs = the<LibrariesForLibs>()

dependencies {
    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.koin.annotations)
    "kspCommonMainMetadata"(libs.koin.ksp.compiler)
    "kspJvm"(libs.koin.ksp.compiler)

    if (kotlin.targets.findByName("iosArm64") != null)
        "kspIosArm64"(libs.koin.ksp.compiler)
}