plugins {
    `kotlin-dsl`
    id("dependencies")
}

group = "service-deploy"
version = "SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    plugins.register("service-deploy") {
        id = "service-deploy"
        implementationClass = "DeployPlugin"
    }
}

dependencies {
    implementation(Deps.Plugins.Dependencies.Classpath)
    implementation(Deps.Plugins.Ssh.Classpath)
    implementation(Deps.Plugins.Shadow.Classpath)
}