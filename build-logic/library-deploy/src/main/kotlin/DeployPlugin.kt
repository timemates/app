import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.the
import org.gradle.util.GUtil.loadProperties
import java.io.File
import java.util.*

class DeployPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.apply(plugin = Deps.Plugins.MavenPublish.Id)

        val configuration = target.extensions.create<DeployExtension>(name = "deploy")

        target.afterEvaluate {
            configuration.apply {
                if (ignore) return@afterEvaluate
                host ?: error("`host` should be defined in `deploy`")
                deployPath ?: error("`deployPath` should be defined in `deploy`")
                componentName ?: error("`componentName` should be defined in `deploy`")
                name ?: error("`name` should be defined in `deploy`")
                description ?: error("`description` should be defined in `deploy`")
            }


            project.the<PublishingExtension>().apply {
                publications {
                    create<MavenPublication>("deploy") {
                        group = configuration.group ?: project.group
                        artifactId = configuration.artifactId ?: project.name
                        version = configuration.version ?: error("shouldn't be null")

                        pom {
                            name.set(configuration.name ?: error("shouldn't be null"))
                            description.set(configuration.description ?: error("shouldn't be null"))
                        }
                        from(components[configuration.componentName ?: error("shouldn't be null")])
                    }
                }
                repositories {
                    maven {
                        name = configuration.name ?: error("shouldn't be null")
                        version = configuration.version ?: error("shouldn't be null")

                        url = uri(
                            "sftp://${configuration.host}:22/${configuration.deployPath}"
                        )

                        credentials {
                            username = configuration.user
                            password = configuration.password
                        }
                    }
                }
            }
        }
    }

    private fun Project.properties(): Properties? =
        rootProject.file("deploy.properties")
            .takeIf(File::exists)
            ?.let(::loadProperties)
}