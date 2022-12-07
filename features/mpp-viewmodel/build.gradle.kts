plugins {
    id(Deps.Plugins.Configuration.Kotlin.Mpp)
    id(Deps.Plugins.Android.Library)
}

kotlin {
    android()

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(Deps.Libs.Androidx.LifecycleViewModelKtx)
            }
        }
    }
}

android {
    compileSdk = Deps.compileSdkVersion

    sourceSets {
        getByName("main") {
            manifest {
                srcFile("src/androidMain/AndroidManifest.xml")
            }
        }
    }
}

dependencies {
    commonMainApi(Deps.Libs.Kotlinx.Coroutines)
}