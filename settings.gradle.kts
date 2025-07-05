enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.y9vad9.com")
    }
}

rootProject.name = "timemate-app"

includeBuild("build-conventions")

include(
    ":feature:core:domain",
)

include(
    ":feature:tasks:domain",
    ":feature:tasks:integration",
    ":feature:tasks:presentation",
    ":feature:tasks:presentation:compose-ui",
    ":feature:tasks:dependencies",
)

include(
    ":feature:timers:domain",
    ":feature:timers:integration",
    ":feature:timers:presentation",
    ":feature:timers:presentation:compose-ui",
    ":feature:timers:dependencies",
)

include(
    ":foundation:validation-ext",
)