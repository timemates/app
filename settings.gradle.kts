pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.y9vad9.com")
    }
}

rootProject.name = "kotlin-project-template"

includeBuild("build-logic/dependencies")
includeBuild("build-logic/configuration")
includeBuild("build-logic/service-deploy")
//includeBuild("buildUtils/library-deploy")
