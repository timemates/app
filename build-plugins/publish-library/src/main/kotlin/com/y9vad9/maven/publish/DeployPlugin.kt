package com.y9vad9.maven.publish

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.the

class DeployPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.apply(plugin = "maven-publish")

        val configuration = target.extensions.create<LibraryDeployExtension>(name = "deployLibrary")

        target.afterEvaluate {
            configuration.targets.forEach { (tag, data) ->
                when (data) {
                    is SshMavenDeployScope -> deployToSsh(tag, data)
                    is GitHubPackagesDeployScope -> deployToGitHub(tag, data)
                }
            }
        }
    }

    private fun Project.deployToSsh(tag: String, data: SshMavenDeployScope) {
        data.host ?: return
        data.deployPath ?: error("`deployPath` should be defined in `deploy`")
        data.componentName ?: error("`componentName` should be defined in `deploy`")
        data.name ?: error("`name` should be defined in `deploy`")
        data.description ?: error("`description` should be defined in `deploy`")

        project.the<PublishingExtension>().apply {
            publications {
                create<MavenPublication>("deploy to $tag") {
                    group = data.group ?: project.group
                    artifactId = data.artifactId ?: project.name
                    version = data.version ?: error("shouldn't be null")

                    pom {
                        name.set(data.name ?: error("shouldn't be null"))
                        description.set(data.description ?: error("shouldn't be null"))
                    }
                    from(components[data.componentName ?: error("shouldn't be null")])
                }
            }
            repositories {
                maven {
                    name = data.name ?: error("shouldn't be null")
                    version = data.version ?: error("shouldn't be null")

                    url = uri(
                        "sftp://${data.host}:22/${data.deployPath}"
                    )

                    credentials {
                        username = data.user
                        password = data.password
                    }
                }
            }
        }
    }

    private fun Project.deployToGitHub(tag: String, data: GitHubPackagesDeployScope) {
        data.githubUserName ?: return logger.info(
            "GitHub username was not specified for $tag, publish task is omitted."
        )
        data.githubToken ?: error("`gitHubToken` should be defined for github publication")
        data.githubPackagesUrl ?: error("`gitHubPackagesUrl` should be defined for github publication")
        data.componentName ?: error("`componentName` should be defined for github publication")
        data.name ?: error("`name` should be defined for github publication")
        data.description ?: error("`description`should be defined for github publication")

        project.the<PublishingExtension>().apply {
            publications {
                create<MavenPublication>("deploy to $tag") {
                    group = data.group ?: project.group
                    artifactId = data.artifactId ?: project.name
                    version = data.version ?: error("shouldn't be null")

                    pom {
                        name.set(data.name ?: error("shouldn't be null"))
                        description.set(data.description ?: error("shouldn't be null"))
                    }
                    from(components[data.componentName ?: error("shouldn't be null")])
                }
            }
            repositories {
                maven {
                    name = data.name ?: error("shouldn't be null")
                    version = data.version ?: error("shouldn't be null")

                    url = uri(data.githubPackagesUrl!!)

                    credentials {
                        username = data.githubUserName
                        password = data.githubToken
                    }
                }
            }
        }
    }
}