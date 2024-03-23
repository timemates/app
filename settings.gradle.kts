enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.timemates.org/releases")
        maven("https://maven.timemates.org/dev")
    }
}

rootProject.name = "timemates-app"

includeBuild("build-conventions")

include(
    ":core:localization",
    ":core:localization:compose",
)

include(
    ":core:style-system",
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
    ":foundation:smart-value",
)

include(
    ":core:navigation",
)

//include(
//    ":preview",
//)

include(
    ":platform:desktop",
    ":platform:android",
    ":platform:common",
)

include(
    ":core:types:serializable",
    ":core:ui",
)

include(
    ":feature:splash:domain",
    ":feature:splash:presentation",
    ":feature:splash:adapters",
    ":feature:splash:dependencies",
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
