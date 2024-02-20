plugins {
    id(libs.plugins.configurations.multiplatform.library.get().pluginId)
    alias(libs.plugins.cashapp.sqldelight)
}

android {
    namespace = "org.timemates.app.authorization.data.database"
}

sqldelight {
    databases {
        create("TimeMatesAuthorizations") {
            generateAsync.set(true)
            packageName.set("io.timemates.data.database")
        }
    }
}