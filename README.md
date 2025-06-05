# <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Kotlin_Icon_2021.svg/2048px-Kotlin_Icon_2021.svg.png" width=24 height=24 /> Kotlin Project Template
Project Template for convenient project setup using [convention plugins](https://docs.gradle.org/current/samples/sample_convention_plugins.html#compiling_convention_plugins)
and [version catalogs](https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog).

## Motivation
Every time I create a new project, I do a lot of routine work, so this repository should decrease amount of this work.

## Initializing
- `settings.gradle.kts`: Set your root project name
- `gradle/libs.versions.toml`: Add your dependencies

> **Note** <br>
> [TYPESAFE_PROJECT_ACCESSORS](https://docs.gradle.org/current/userguide/declaring_dependencies.html#sec:type-safe-project-accessors)
> are enabled by default. If you don't need this feature, remove it from `settings.gradle.kts`.

## Builtins
### Build conventions
This template also provides some useful [build conventions](build-conventions/src/main/kotlin).

#### How to use
Example of `build.gradle.kts` usage:
```kotlin
plugins {
    id(libs.plugins.conventions.jvm.get().pluginId)
    // or
    id("jvm-convention")
}
```
