plugins {
    `kotlin-dsl`
    id("dependencies")
}

group = "library-deploy"
version = "SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    plugins.register("library-deploy") {
        id = "library-deploy"
        implementationClass = "DeployPlugin"
    }
}

dependencies {
    implementation(Deps.Plugins.Dependencies.Classpath)
    implementation(Deps.Plugins.Ssh.Classpath)
    implementation(Deps.Plugins.Shadow.Classpath)
}