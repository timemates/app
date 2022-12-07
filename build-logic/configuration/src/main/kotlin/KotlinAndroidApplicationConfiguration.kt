import org.gradle.api.Plugin
import org.gradle.api.Project


class KotlinAndroidApplicationConfiguration : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply(Deps.Plugins.Android.Application)
        target.plugins.apply(Deps.Plugins.Kotlin.Android)
    }
}
