plugins {
    id(Deps.Plugins.Configuration.Kotlin.Mpp)
    id(Deps.Plugins.Android.Library)
    id(Deps.Plugins.SQLDelight.Id)
}

kotlin {
    android()

    sourceSets {
        val androidMain by getting {
            dependencies {
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
}

sqldelight {
    database("Pomodoro") {
        packageName = "com.y9vad9.pomodoro.client.db"
        sourceFolders = listOf("sqldelight")
        schemaOutputDirectory = file("build/dbs")
        dialect = "sqlite:3.25"
    }
}

dependencies {
    commonMainImplementation(project(Deps.Modules.UseCases))
    commonMainImplementation(Deps.Libs.Kotlinx.Coroutines)
}