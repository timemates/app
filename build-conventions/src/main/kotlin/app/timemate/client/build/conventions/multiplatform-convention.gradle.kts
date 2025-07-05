package app.timemate.client.build.conventions

import org.gradle.internal.os.OperatingSystem

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()
    js {
        browser()
    }

    jvmToolchain(11)

    sourceSets {
        all {
            sourceSets {
                all {
                    languageSettings.optIn("kotlin.time.ExperimentalTime")
                    languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
                }
            }
        }
    }
}