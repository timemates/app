import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.multiplatform) apply false
}

allprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += listOf(
                "-P",
                "-Pandroidx.enableComposeCompilerMetrics=true",
                "-Pandroidx.enableComposeCompilerReports=true",
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=build/compose_metrics",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=build/compose_reports"
            )
        }
    }
}