enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
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

rootProject.name = "timemates-app"

includeBuild("build-plugins/publish-library")
includeBuild("build-plugins/koin-configuration")

include(
    ":core",
    ":data",
    ":data:sdk",
    ":data:database",
    ":style-system",
    ":shared-deps",
)

include(
    ":features:mvi",
)

include(
    ":presentation",
)

include(":platforms:desktop")
