plugins {
    `kotlin-dsl`
}

group = "koin-configuration"
version = "SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    plugins.register("koin-configuration") {
        id = "koin-configuration"
        implementationClass = "com.y9vad9.koin.plugin.KoinPlugin"
    }
}