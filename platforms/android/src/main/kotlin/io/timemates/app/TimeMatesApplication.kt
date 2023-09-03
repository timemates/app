package io.timemates.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.timemates.api.rsocket.RSocketTimeMatesRequestsEngine
import io.timemates.app.authorization.dependencies.AuthorizationDataModule
import io.timemates.app.authorization.dependencies.screens.AfterStartModule
import io.timemates.app.authorization.dependencies.screens.ConfigureAccountModule
import io.timemates.app.authorization.dependencies.screens.ConfirmAuthorizationModule
import io.timemates.app.authorization.dependencies.screens.InitialAuthorizationModule
import io.timemates.app.authorization.dependencies.screens.NewAccountInfoModule
import io.timemates.app.authorization.dependencies.screens.StartAuthorizationModule
import io.timemates.app.core.handler.OnAuthorizationFailedHandler
import io.timemates.app.foundation.time.SystemUTCTimeProvider
import io.timemates.app.foundation.time.TimeProvider
import io.timemates.app.users.data.database.TimeMatesUsers
import io.timemates.data.database.TimeMatesAuthorizations
import io.timemates.sdk.common.engine.TimeMatesRequestsEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.koin.ksp.generated.module
import androidx.multidex.MultiDexApplication
import androidx.multidex.MultiDex
import android.content.Context
import io.timemates.app.authorization.dependencies.screens.InitialAuthorizationModule
import io.timemates.app.foundation.time.SystemUTCTimeProvider
import io.timemates.app.foundation.time.TimeProvider
import io.timemates.app.timers.dependencies.TimersDataModule
import io.timemates.app.timers.dependencies.screens.TimerCreationModule
import io.timemates.app.timers.dependencies.screens.TimerSettingsModule
import io.timemates.app.timers.dependencies.screens.TimersListModule
import io.timemates.app.users.data.database.TimeMatesUsers

class TimeMatesApplication : MultiDexApplication() {

    companion object {
        internal val AUTH_FAILED_CHANNEL: Channel<Unit> = Channel()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)

        startKoin {
            val platformModule = module {
                single<TimeMatesRequestsEngine> {
                    RSocketTimeMatesRequestsEngine(coroutineScope = CoroutineScope(Dispatchers.IO))
                }

                single<OnAuthorizationFailedHandler> {
                    OnAuthorizationFailedHandler { exception ->
                        exception.printStackTrace()
                        AUTH_FAILED_CHANNEL.trySend(Unit)
                    }
                }

                single<SqlDriver>(qualifier = qualifier("authorization")) {
                    AndroidSqliteDriver(
                        schema = TimeMatesAuthorizations.Schema.synchronous(),
                        context = applicationContext,
                        name = "authorizations.db",
                    )
                }

                single<SqlDriver>(qualifier = qualifier("users")) {
                    AndroidSqliteDriver(
                        schema = TimeMatesUsers.Schema.synchronous(),
                        context = applicationContext,
                        name = "users.db",
                    )
                }

                single<TimeProvider> {
                    SystemUTCTimeProvider()
                }
            }

            modules(
                platformModule,
                InitialAuthorizationModule().module,
                AuthorizationDataModule().module,
                ConfirmAuthorizationModule().module,
                StartAuthorizationModule().module,
                AfterStartModule().module,
                NewAccountInfoModule().module,
                ConfigureAccountModule().module,
                TimersListModule().module,
                TimerCreationModule().module,
                TimerSettingsModule().module,
            )
        }
    }
}
