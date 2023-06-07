# Gradle Plugin for Library Deployment
The Gradle plugin for library deployment is a powerful tool that simplifies the process of deploying
libraries to different targets. It provides a DSL extension that allows you to configure and
customize the deployment targets for your libraries.

## Configuration
To configure the library deployment, you need to apply the plugin and set up the deployment
targets using the provided DSL. Here's an example of how to configure the library deployment:
```kotlin
plugins {
    alias(libs.plugins.bundled.publish.library)
}

deploy {
    ssh("y9vad9-maven") {
        host = "example.com"
        componentName = "my-library"
        group = "com.example"
        artifactId = "my-library"
        version = "1.0.0"
        deployPath = "/path/to/deployment"
    }

    github("github-packages") {
        mavenUrl = "https://maven.pkg.github.com/your-username/repo"
        githubUserName = "your-username"
        githubToken = "your-personal-access-token"
        componentName = "my-library"
        group = "com.example"
        artifactId = "my-library"
        version = "1.0.0"
        name = "My Library"
        description = "A description of my library"
    }
}

```