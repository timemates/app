import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.the
import org.gradle.util.GUtil.loadProperties
import org.hidetake.groovy.ssh.connection.AllowAnyHosts
import org.hidetake.groovy.ssh.core.Remote
import java.io.File
import java.util.*

class DeployPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.apply(plugin = Deps.Plugins.Ssh.Id)
        target.apply(plugin = Deps.Plugins.Application.Id)
        target.apply(plugin = Deps.Plugins.Shadow.Id)

        val extension = target.extensions.create<DeployExtension>(name = "deploy")

        target.afterEvaluate {
            val default = extension.targets["default"]
            extension.targets.filter { it.key != "default" }.forEach { configuration ->
                configuration.value.apply {
                    host ?: default?.host ?: error ("`host` should be defined in `deploy`")
                    destination ?: default?.destination ?: error("`destination` should be defined in `deploy`")
                    mainClass ?: default?.mainClass ?: error("`mainClass` should be defined in `deploy`")
                    serviceName ?: default?.serviceName ?: error("`service name` should be defined in `deploy`")
                }

                val shadowJar = target.tasks.create<ShadowJar>("${configuration.key}ShadowJar") {
                    archiveFileName.set(configuration.value.archiveName ?: default?.mainClass)
                    mergeServiceFiles()
                    manifest {
                        attributes(mapOf("Main-Class" to (configuration.value.mainClass ?: default?.mainClass)))
                    }
                }

                val webServer = Remote(
                    mapOf(
                        "host" to (configuration.value.host ?: default?.host),
                        "user" to (configuration.value.user ?: default?.user),
                        "password" to (configuration.value.password ?: default?.password),
                        "knownHosts" to ((configuration.value.knownHostsFile ?: default?.knownHostsFile)?.let(::File) ?: AllowAnyHosts.instance)
                    )
                )

                target.extensions.create<SshSessionExtension>("${configuration.key}SshSession", target, webServer)

                target.task("${configuration.key}Deploy") {
                    group = "deploy"
                    dependsOn(shadowJar)

                    doLast {
                        target.extensions.getByName<SshSessionExtension>("${configuration.key}SshSession").invoke {
                            put(
                                hashMapOf(
                                    "from" to shadowJar.archiveFile.get().asFile,
                                    "into" to configuration.value.destination
                                )
                            )
                            execute("systemctl restart ${configuration.value.serviceName ?: default?.serviceName}")
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
