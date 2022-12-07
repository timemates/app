plugins {
    `kotlin-dsl`
}

group = "dependencies"
version = "SNAPSHOT"

gradlePlugin {
    plugins.register("dependencies") {
        id = "dependencies"
        implementationClass = "unused.GradlePlugin"
    }
}

repositories {
    mavenCentral()
}
