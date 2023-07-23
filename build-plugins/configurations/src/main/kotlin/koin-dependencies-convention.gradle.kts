plugins {
    id("multiplatform-library-convention")
    id("com.google.devtools.ksp")
}

version = "SNAPSHOT"

val koinVersion = "3.4.2"
val koinAnnotationsVersion = "1.2.2"

dependencies {
    commonMainImplementation("io.insert-koin:koin-core:$koinVersion")
    commonMainImplementation("io.insert-koin:koin-annotations:$koinAnnotationsVersion")

    val kspCompiler = "io.insert-koin:koin-ksp-compiler:$koinAnnotationsVersion"

    add("kspCommonMainMetadata", kspCompiler)
    add("kspJvm", kspCompiler)
    add("kspAndroid", kspCompiler)
}