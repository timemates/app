package com.y9vad9.maven.publish

import com.y9vad9.maven.publish.annotation.PublishDsl

/**
 * A DSL extension class for configuring library deployments.
 */
@PublishDsl
open class LibraryDeployExtension {
    /**
     * Internal mutable map that stores the deployment targets.
     */
    internal val targets: MutableMap<String, MavenDeployScope> = mutableMapOf()

    /**
     * Defines a new SSH deployment target with the given [tag] and configuration [block].
     *
     * Example usage:
     * ```
     * ssh("production") {
     *     host = "example.com"
     *     componentName = "my-library"
     *     group = "com.example"
     *     artifactId = "my-library"
     *     version = "1.0.0"
     *     deployPath = "/path/to/deployment"
     * }
     * ```
     *
     * @param tag The tag associated with the deployment target.
     * @param block The configuration block to customize the SSH deployment target.
     */
    fun ssh(tag: String, block: SshMavenDeployScope.() -> Unit) {
        targets[tag] = SshMavenDeployScope().apply(block)
    }

    /**
     * Defines a new GitHub Packages deployment target with the given [tag] and configuration [block].
     *
     * Example usage:
     * ```
     * github("production") {
     *     mavenUrl = "https://maven.pkg.github.com/your-username/repo"
     *     githubUserName = "your-username"
     *     githubToken = "your-personal-access-token"
     *     componentName = "my-library"
     *     group = "com.example"
     *     artifactId = "my-library"
     *     version = "1.0.0"
     *     name = "My Library"
     *     description = "A description of my library"
     * }
     * ```
     *
     * @param tag The tag associated with the deployment target.
     * @param block The configuration block to customize the GitHub Packages deployment target.
     */
    fun github(tag: String, block: GitHubPackagesDeployScope.() -> Unit) {
        targets[tag] = GitHubPackagesDeployScope().apply(block)
    }
}
