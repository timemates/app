import org.gradle.api.Plugin
import org.gradle.api.Project


class KotlinMultiplatformConfiguration : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply(Deps.Plugins.Kotlin.Multiplatform)
    }
}
