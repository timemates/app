plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.cashapp.sqldelight)
    alias(libs.plugins.android.library)
}

kotlin {
    jvm()
    android()
}

android {
    compileSdk = libs.versions.android.target.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
    }

    namespace = "io.timemates.app.users.data.database"
}

sqldelight {
    databases {
        create("TimeMatesUsers") {
            generateAsync.set(true)
            packageName.set("io.timemates.app.users.data.database")
        }
    }
}