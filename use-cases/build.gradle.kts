plugins {
    id(Deps.Plugins.Configuration.Kotlin.Mpp)
    id(Deps.Plugins.Android.Library)
}

kotlin {
    android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Libs.Kotlinx.Coroutines)
            }
        }
    }
}

dependencies {
    commonMainApi(project(Deps.Modules.Domain))
}

android {
    compileSdk = Deps.compileSdkVersion
}