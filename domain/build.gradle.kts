plugins {
    id(Deps.Plugins.Configuration.Kotlin.Mpp)
    id(Deps.Plugins.Android.Library)
}

kotlin {
    android()
}

android {
    compileSdk = Deps.compileSdkVersion
}