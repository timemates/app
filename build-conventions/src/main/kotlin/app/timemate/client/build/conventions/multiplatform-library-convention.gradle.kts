package app.timemate.client.build.conventions

import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    id("app.timemate.client.build.conventions.multiplatform-convention")
}

kotlin {
    explicitApi = ExplicitApiMode.Strict
}