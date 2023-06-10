package com.y9vad9.koin.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class KoinPlugin : Plugin<Project> {
    companion object {
        const val koinVersion = "3.4.2"
        const val koinAnnotations = "1.2.2"
    }
    override fun apply(target: Project): Unit = with(target) {
        apply(plugin = "com.google.devtools.ksp")

        dependencies.add(
            "commonMainImplementation",
            "io.insert-koin:koin-core:$koinVersion",
        )

        dependencies.add(
            "commonMainImplementation",
            "io.insert-koin:koin-annotations:$koinAnnotations"
        )

        dependencies.add(
            "ksp",
            "io.insert-koin:koin-ksp-compiler:$koinAnnotations"
        )
    }
}