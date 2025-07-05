package app.timemate.client.build.conventions

import org.gradle.internal.os.OperatingSystem

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

    if (OperatingSystem.current().isMacOsX)
        iosArm64()

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