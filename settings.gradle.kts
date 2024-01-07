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
        mavenCentral()
        google()
        maven("https://maven.timemates.io")
    }
}

rootProject.name = "timemates-app"

includeBuild("build-conventions")

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
    ":foundation:time",
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
    ":platforms:common",
)

include(
    ":feature:common:domain",
    ":feature:common:presentation",
)

include(
    ":feature:system:domain",
    ":feature:system:presentation",
    ":feature:system:adapters",
    ":feature:system:dependencies",
)

include(
    ":feature:authorization:domain",
    ":feature:authorization:presentation",
    ":feature:authorization:data:database",
    ":feature:authorization:data",
    ":feature:authorization:dependencies",
)

include(
    ":feature:users:domain",
    ":feature:users:presentation",
    ":feature:users:data:database",
    ":feature:users:data",
    ":feature:users:dependencies",
)

include(
    ":feature:timers:domain",
    ":feature:timers:presentation",
    ":feature:timers:data:database",
    ":feature:timers:data",
    ":feature:timers:dependencies",
)
