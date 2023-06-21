enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.y9vad9.com")
    }
}

rootProject.name = "timemates-app"

includeBuild("build-plugins/publish-library")
includeBuild("build-plugins/configurations")

include(
    ":core",
    ":data",
    ":data:sdk",
    ":data:database",
    ":style-system",
)

include(
    ":foundation:mvi",
    ":foundation:mvi:koin-compose",
    ":foundation:viewmodel",
    ":foundation:random",
)

include(
    ":ui-core",
    ":navigation",
)

include(
    ":platforms:desktop",
    ":platforms:android",
)

include(
    ":feature:authorization:domain",
    ":feature:authorization:presentation",
    ":feature:authorization:data:sdk",
    ":feature:authorization:data:database",
    ":feature:authorization:data",
    ":feature:authorization:dependencies",
)