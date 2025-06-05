@file:Suppress("unused")

/**
 * Defines access to all available convention plugin IDs used in this Gradle project.
 *
 * These constants are manually updated and help avoid typos or duplication when referencing
 * convention plugins from the included build `build-conventions`.
 *
 * Example usage:
 * ```kotlin
 * plugins {
 *     id(conventions.jvm)
 *     id(conventions.feature.di)
 *     id(conventions.feature.database)
 * }
 * ```
 */
val conventions: ConventionNamespace = ConventionNamespace()

/**
 * Top-level namespace that organizes convention plugins into meaningful groups
 * (e.g., `feature`, `jvm`, `multiplatform`, etc.).
 */
class ConventionNamespace internal constructor(
    /** ID of the base JVM convention plugin. */
    val jvm: String = "app.timemate.client.build.conventions.jvm-convention",

    /** ID of the shared test conventions plugin. */
    val tests: String = "app.timemate.client.build.conventions.tests-convention",

    /** Convention plugins used for feature modules (e.g., DI, Compose, Domain). */
    val feature: FeatureNamespace = FeatureNamespace(),

    /** Convention plugins used in Kotlin Multiplatform projects. */
    val multiplatform: MultiplatformNamespace = MultiplatformNamespace()
)

/**
 * Convention plugins related to individual feature modules.
 */
class FeatureNamespace internal constructor(
    /** Convention plugin for DI setup in feature modules. */
    val di: String = "app.timemate.client.build.conventions.feature.di-convention",

    /** Convention plugin for domain-layer setup in feature modules. */
    val domain: String = "app.timemate.client.build.conventions.feature.domain-convention",

    /** Convention plugins related to integration-layer concerns like database, network, etc. */
    val integration: String = "app.timemate.client.build.conventions.feature.integration-convention",

    val database: String = "app.timemate.client.build.conventions.feature.integration-database-convention",

    val presentation: String = "app.timemate.client.build.conventions.feature.presentation-convention",

    /** Convention plugins related to Compose UI setup in feature modules. */
    val compose: FeatureComposeNamespace = FeatureComposeNamespace(),
)

/**
 * Convention plugins related to Jetpack Compose UI within feature modules.
 */
class FeatureComposeNamespace internal constructor(
    /** Convention plugin for Compose UI setup. */
    val ui: String = "app.timemate.client.build.conventions.feature.compose-ui-convention"
)


/**
 * Convention plugins used in Kotlin Multiplatform projects.
 */
class MultiplatformNamespace internal constructor(
    /** Convention plugin for setting up shared multiplatform libraries. */
    val library: String = "app.timemate.client.build.conventions.multiplatform-library-convention"
)
