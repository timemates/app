package org.timemates.app

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.timemates.app.common.initializeAppDependencies
import org.timemates.app.foundation.time.SystemUTCTimeProvider
import io.timemates.credentials.DesktopCredentialsStorage
import io.timemates.sdk.common.exceptions.UnauthorizedException
import kotlinx.coroutines.channels.Channel
import kotlin.io.path.pathString

fun main() {
    // used for navigation to auth when authorization expired / deactivated
    val authorizationFailedChannel: Channel<UnauthorizedException> = Channel()

    val driver = JdbcSqliteDriver(
        "jdbc:sqlite:${AppDirectory.pathString}/data.db",
    )

    initializeAppDependencies(
        SystemUTCTimeProvider(),
        authorizationFailedChannel,
        driver,
        driver,
        DesktopCredentialsStorage(),
    )
    startUi(authorizationFailedChannel)
}