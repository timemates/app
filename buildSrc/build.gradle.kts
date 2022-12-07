plugins {
    id("dependencies")
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies  {
    implementation(Deps.Plugins.Dependencies.Classpath)
}
