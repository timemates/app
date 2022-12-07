plugins {
    id(Deps.Plugins.Configuration.Kotlin.Mpp)
    id(Deps.Plugins.Android.Library)
}

kotlin {
    android()

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(Deps.Libs.Google.PlayServicesAuth)
                implementation(Deps.Libs.SQLDelight.AndroiDriver)
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

    defaultConfig {
        minSdk = Deps.minSdkVersion
    }
}

dependencies {
    commonMainApi(project(Deps.Modules.Features.ViewModelMpp))
    commonMainImplementation(project(Deps.Modules.Domain))
    commonMainImplementation(project(Deps.Modules.UseCases))
}