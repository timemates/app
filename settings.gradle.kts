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
        maven("https://maven.timemates.io")
    }
}

rootProject.name = "timemates-app"

includeBuild("build-plugins/publish-library")
includeBuild("build-plugins/configurations")

include(
    ":localization",
    ":localization:compose",
)

include(
    ":style-system",
)

include(
    ":foundation:mvi",
    ":foundation:mvi:koin-compose",
    ":foundation:viewmodel",
    ":foundation:random",
    ":foundation:validation",
    ":foundation:stdlib-ext",
    ":foundation:system-tray",
    ":foundation:shimmer-compose",
)

include(
    ":navigation",
)

include(
    ":preview",
)

include(
    ":platforms:desktop",
    ":platforms:android",
)

include(
    ":feature:common:domain",
    ":feature:common:presentation",
)

include(
    ":feature:authorization:domain",
    ":feature:authorization:presentation",
    ":feature:authorization:data:sdk",
    ":feature:authorization:data:database",
    ":feature:authorization:data",
    ":feature:authorization:dependencies",
)

include(
    ":feature:users:domain",
    ":feature:users:presentation",
    ":feature:users:data:sdk",
    ":feature:users:data:database",
    ":feature:users:data",
    ":feature:users:dependencies",
)

include(
    ":feature:timers:domain",
    ":feature:timers:presentation",
    ":feature:timers:data:sdk",
    ":feature:timers:data:database",
    ":feature:timers:data",
    ":feature:timers:dependencies",
)
