package com.y9vad9.maven.publish

import com.y9vad9.maven.publish.annotation.PublishDsl

@PublishDsl
class GitHubPackagesDeployScope : MavenDeployScope {
    /**
     * The URL of the Maven repository in GitHub Packages.
     */
    var githubPackagesUrl: String? = null

    /**
     * The GitHub username associated with the deployment.
     */
    var githubUserName: String? = null

    /**
     * The GitHub personal access token for authentication.
     */
    var githubToken: String? = null

    /**
     * The name of the component being deployed.
     */
    var componentName: String? = null

    /**
     * The Maven group ID of the artifact.
     */
    var group: String? = null

    /**
     * The Maven artifact ID.
     */
    var artifactId: String? = null

    /**
     * The version of the artifact.
     */
    var version: String? = null

    /**
     * The name of the deployment.
     */
    var name: String? = null

    /**
     * The description of the deployment.
     */
    var description: String? = null
}
