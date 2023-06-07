package com.y9vad9.maven.publish

import com.y9vad9.maven.publish.annotation.PublishDsl

@PublishDsl
class SshMavenDeployScope : MavenDeployScope {
    /**
     * The hostname or IP address of the SSH server.
     */
    var host: String? = null

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

    /**
     * The path on the remote server where the artifact should be deployed.
     */
    var deployPath: String? = null

    /**
     * The SSH username for authentication (optional).
     */
    var user: String? = null

    /**
     * The SSH password for authentication (optional).
     */
    var password: String? = null
}
