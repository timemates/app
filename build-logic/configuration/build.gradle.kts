plugins {
    `kotlin-dsl`
    id("dependencies")
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(Deps.Plugins.Dependencies.Classpath)
    implementation(Deps.Plugins.Android.Classpath)
    implementation(Deps.Plugins.Kotlin.Classpath)
}

gradlePlugin {
    plugins.register("k-mpp") {
        id = "k-mpp"
        implementationClass = "KotlinMultiplatformConfiguration"
    }
    plugins.register("k-jvm") {
        id = "k-jvm"
        implementationClass = "KotlinJvmConfiguration"
    }
    plugins.register("k-js") {
        id = "k-js"
        implementationClass = "KotlinJsConfiguration"
    }
    plugins.register("k-android-app") {
        id = "k-android-app"
        implementationClass = "KotlinAndroidApplicationConfiguration"
    }
    plugins.register("k-android-library") {
        id = "k-android-library"
        implementationClass = "KotlinAndroidLibraryConfiguration"
    }
}
