package io.timemates.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.timemates.app.common.initializeAppDependencies
import io.timemates.app.foundation.time.SystemUTCTimeProvider
import io.timemates.app.users.data.database.TimeMatesUsers
import io.timemates.data.database.TimeMatesAuthorizations
import io.timemates.sdk.common.exceptions.UnauthorizedException
import kotlinx.coroutines.channels.Channel

class TimeMatesApplication : MultiDexApplication() {

    companion object {
        internal val onAuthFailedChannel: Channel<UnauthorizedException> = Channel()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)

        val (authDriver, usersDriver) = listOf(
            "authorization" to TimeMatesAuthorizations.Schema,
            "users" to TimeMatesUsers.Schema,
        ).map { (name, schema) ->
            AndroidSqliteDriver(
                schema = schema.synchronous(),
                context = applicationContext,
                name = name,
            )
        }

        initializeAppDependencies(
            SystemUTCTimeProvider(),
            onAuthFailedChannel,
            authDriver,
            usersDriver,
        )
    }
}
