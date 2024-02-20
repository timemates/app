plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
    alias(libs.plugins.cashapp.sqldelight)
}

android {
    namespace = "org.timemates.app.users.data.database"
}

sqldelight {
    databases {
        create("TimeMatesUsers") {
            generateAsync.set(true)
            packageName.set("org.timemates.app.users.data.database")
        }
    }
}