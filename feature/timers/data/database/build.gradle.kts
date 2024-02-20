plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
    alias(libs.plugins.cashapp.sqldelight)
}
android {
    namespace = "org.timemates.app.data.database"
}

sqldelight {
    databases {
        create("TimeMatesUsers") {
            generateAsync.set(true)
            packageName.set("io.timemates.data.database")
        }
    }
}