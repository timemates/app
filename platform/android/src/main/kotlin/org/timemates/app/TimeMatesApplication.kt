package org.timemates.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.timemates.app.common.initializeAppDependencies
import org.timemates.app.credentials.AndroidEncryptedPrefsCredentials
import org.timemates.app.foundation.time.SystemUTCTimeProvider
import org.timemates.app.users.data.database.TimeMatesUsers
import org.timemates.credentials.CredentialsStorage
import io.timemates.data.database.TimeMatesAuthorizations
import io.timemates.data.database.TimeMatesTimers
import org.timemates.sdk.common.exceptions.UnauthorizedException
import kotlinx.coroutines.channels.Channel

class TimeMatesApplication : MultiDexApplication() {

    companion object {
        internal val onAuthFailedChannel: Channel<UnauthorizedException> = Channel()
    }

    override fun onCreate() {
        super.onCreate()
        val (authDriver, usersDriver, timersDriver) = listOf(
            "authorization" to TimeMatesAuthorizations.Schema,
            "users" to TimeMatesUsers.Schema,
            "timers" to TimeMatesTimers.Schema,
        ).map { (name, schema) ->
            AndroidSqliteDriver(
                schema = schema.synchronous(),
                context = applicationContext,
                name = name,
            )
        }

        val credentialsStorage: CredentialsStorage = AndroidEncryptedPrefsCredentials(applicationContext)

        initializeAppDependencies(
            SystemUTCTimeProvider(),
            onAuthFailedChannel,
            authDriver,
            usersDriver,
            timersDriver,
            credentialsStorage,
        )
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }
}
