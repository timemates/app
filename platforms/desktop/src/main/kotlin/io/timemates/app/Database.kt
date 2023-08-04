package io.timemates.app

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.y9vad9.secure.credentials.CredentialsStorage
import com.y9vad9.secure.credentials.getStringOrSet
import io.timemates.app.foundation.random.nextString
import io.timemates.app.storage.AppDirectory
import io.timemates.app.users.data.database.TimeMatesUsers
import io.timemates.data.database.TimeMatesAuthorizations
import java.security.SecureRandom
import kotlin.io.path.pathString
import kotlin.random.asKotlinRandom

/**
 * Creates a SQLite database with the specified folder path.
 *
 * @param folderPath The path to the folder where the SQLite database file will be stored.
 *                   Defaults to the application directory path.
 * @return A [JdbcSqliteDriver] instance representing the created SQLite database driver.
 */
fun createDatabase(folderPath: String = AppDirectory.pathString): JdbcSqliteDriver {
    val random = SecureRandom().asKotlinRandom()
    val credentialsStorage = CredentialsStorage.ofCurrentPlatform(AppConstants.APP_NAME)
        ?: throw IllegalStateException("Current platform does not support safe secrets.")

    credentialsStorage.getStringOrSet("database_encryption_key") {
        random.nextString(256)
    }

    // TODO encryption
    val driver = JdbcSqliteDriver(
        "jdbc:sqlite:${folderPath}/data.db",
    )

    TimeMatesAuthorizations.Schema.create(driver)
    TimeMatesUsers.Schema.create(driver)

    return driver
}