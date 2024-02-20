import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.cashapp.sqldelight) apply false
    alias(libs.plugins.compose.multiplatform) apply false
}

group = "org.timemates.app"

allprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "19"
        }
    }
}
