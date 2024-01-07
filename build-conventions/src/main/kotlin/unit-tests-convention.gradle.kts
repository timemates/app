plugins {
    id("multiplatform-library-convention")
}

val kotlinVersion = KotlinVersion.CURRENT.toString()
val coroutinesVersion = "1.7.1"

kotlin {
    sourceSets {
        jvmTest {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
                implementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
            }
        }
    }
}

dependencies {
    commonTestImplementation("org.jetbrains.kotlin:kotlin-test-common:$kotlinVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}