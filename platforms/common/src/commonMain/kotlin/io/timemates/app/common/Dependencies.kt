package io.timemates.app.common

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import io.timemates.api.rsocket.RSocketTimeMatesRequestsEngine
import io.timemates.app.authorization.dependencies.AuthorizationDataModule
import io.timemates.app.authorization.dependencies.screens.AfterStartModule
import io.timemates.app.authorization.dependencies.screens.ConfigureAccountModule
import io.timemates.app.authorization.dependencies.screens.ConfirmAuthorizationModule
import io.timemates.app.authorization.dependencies.screens.InitialAuthorizationModule
import io.timemates.app.authorization.dependencies.screens.NewAccountInfoModule
import io.timemates.app.authorization.dependencies.screens.StartAuthorizationModule
import io.timemates.app.feature.common.handler.OnAuthorizationFailedHandler
import io.timemates.app.feature.system.dependencies.SystemDataModule
import io.timemates.app.feature.system.dependencies.screens.StartupModule
import io.timemates.app.foundation.time.TimeProvider
import io.timemates.app.timers.dependencies.screens.TimerCreationModule
import io.timemates.app.timers.dependencies.screens.TimerSettingsModule
import io.timemates.app.timers.dependencies.screens.TimersListModule
import io.timemates.data.database.TimeMatesAuthorizations
import io.timemates.sdk.common.engine.TimeMatesRequestsEngine
import io.timemates.sdk.common.exceptions.UnauthorizedException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

/**
 * Initializes the application dependencies using Koin.
 *
 * This method sets up the Koin dependency injection framework to provide instances of various dependencies
 * required by the application. The method specifically configures the injection of the following components:
 * - [TimeMatesRequestsEngine]: An implementation of the TimeMatesRequestsEngine interface that uses RSocket.
 * - [TimeProvider]: An interface providing time-related functionalities.
 * - [OnAuthorizationFailedHandler]: A handler for authorization failures, broadcasting them through a provided channel.
 *
 * @param timeProvider The implementation of the [TimeProvider] interface to be used for time-related functionalities.
 * @param onAuthFailed A [Channel] for handling unauthorized exceptions. Defaults to an empty [Channel].
 */
fun initializeAppDependencies(
    timeProvider: TimeProvider,
    onAuthFailed: Channel<UnauthorizedException>,
    authDriver: SqlDriver,
    usersDriver: SqlDriver,
) {
    runBlocking {
        // we ignore errors here as we assume that there can be failures
        // according to the already existing table
        try {
            TimeMatesAuthorizations.Schema.awaitCreate(authDriver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    startKoin {
        val appModule = module {
            single<TimeMatesRequestsEngine> {
                RSocketTimeMatesRequestsEngine(coroutineScope = CoroutineScope(Dispatchers.IO)) {

                }
            }

            single<SqlDriver>(qualifier("authorization")) {
                authDriver
            }
            single<SqlDriver>(qualifier("users")) {
                usersDriver
            }

            single<TimeProvider> {
                timeProvider
            }

            single<OnAuthorizationFailedHandler> { _ ->
                OnAuthorizationFailedHandler {
                    onAuthFailed.trySend(it)
                }
            }
        }

        modules(
            appModule,
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
            SystemDataModule().module,
            StartupModule().module,
        )
    }
}