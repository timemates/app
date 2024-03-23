package org.timemates.app.common

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import io.timemates.data.database.TimeMatesAuthorizations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.timemates.api.rsocket.RSocketTimeMatesRequestsEngine
import org.timemates.app.authorization.dependencies.AuthorizationModule
import org.timemates.app.feature.common.failures.OnAuthorizationFailedHandler
import org.timemates.app.feature.splash.dependencies.SplashModule
import org.timemates.app.foundation.time.TimeProvider
import org.timemates.app.timers.dependencies.TimersModule
import org.timemates.app.users.dependencies.UsersModule
import org.timemates.credentials.CredentialsStorage
import org.timemates.sdk.common.engine.TimeMatesRequestsEngine
import org.timemates.sdk.common.exceptions.UnauthorizedException

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
    credentialsStorage: CredentialsStorage,
) {
    runBlocking {
        // we ignore errors here as we assume that there can be failures
        // according to the already existing table
        try {
            TimeMatesAuthorizations.Schema.awaitCreate(authDriver)
        } catch (_: Exception) {}
    }

    startKoin {
        val appModule = module {
            single<TimeMatesRequestsEngine> {
                RSocketTimeMatesRequestsEngine(
                    coroutineScope = CoroutineScope(Dispatchers.IO),
                )
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

            single<CredentialsStorage> {
                credentialsStorage
            }

            single<OnAuthorizationFailedHandler> { _ ->
                OnAuthorizationFailedHandler {
                    onAuthFailed.trySend(it)
                }
            }
        }

        modules(
            appModule,
            SplashModule().module,
            AuthorizationModule().module,
            TimersModule().module,
            UsersModule().module,
        )
    }
}