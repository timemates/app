import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("multiplatform-library-convention")
    id("com.google.devtools.ksp")
}

version = "SNAPSHOT"

val libs = the<LibrariesForLibs>()

dependencies {
    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)
}