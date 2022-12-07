pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    plugins {
        id("com.squareup.sqldelight") version "1.5.3"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.y9vad9.com")
    }
}

rootProject.name = "tomadoro-client"

includeBuild("build-logic/dependencies")
includeBuild("build-logic/configuration")
includeBuild("build-logic/service-deploy")
//includeBuild("buildUtils/library-deploy")

include(
    ":viewmodels",
    ":features:mpp-viewmodel",
    ":android",
    ":use-cases",
    ":domain",
    ":adapters:repositories-integration"
)