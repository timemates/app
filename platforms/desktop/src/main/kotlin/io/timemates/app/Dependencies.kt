package io.timemates.app

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.timemates.api.grpc.GrpcTimeMatesRequestsEngine
import io.timemates.api.grpc.factory.DefaultGrpcEngineBuilder
import io.timemates.app.authorization.dependencies.AuthorizationDataModule
import io.timemates.app.authorization.dependencies.screens.AfterStartModule
import io.timemates.app.authorization.dependencies.screens.ConfigureAccountModule
import io.timemates.app.authorization.dependencies.screens.ConfirmAuthorizationModule
import io.timemates.app.authorization.dependencies.screens.NewAccountInfoModule
import io.timemates.app.authorization.dependencies.screens.StartAuthorizationModule
import io.timemates.app.core.handler.OnAuthorizationFailedHandler
import io.timemates.app.users.data.database.TimeMatesUsers
import io.timemates.data.database.TimeMatesAuthorizations
import io.timemates.sdk.common.engine.TimeMatesRequestsEngine
import kotlinx.coroutines.channels.Channel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.koin.ksp.generated.module

/**
 * Initializes the application dependencies using Koin.
 *
 * @param authorizationFailedChannel A [Channel] that will be used to handle authorization failure events.
 */
fun initializeDependencies(
    authorizationFailedChannel: Channel<Unit>
) {
    startKoin {
        val platformModule = module {
            single<SqlDriver>(qualifier = qualifier("authorization")) {
                createDatabase()
            }

            single<TimeMatesRequestsEngine> {
                GrpcTimeMatesRequestsEngine(
                    grpcEngineBuilder = DefaultGrpcEngineBuilder()
                )
            }

            single<OnAuthorizationFailedHandler> {
                OnAuthorizationFailedHandler { exception ->
                    exception.printStackTrace()
                    authorizationFailedChannel.trySend(Unit)
                }
            }
        }
        modules(
            platformModule,
            AuthorizationDataModule().module,
            ConfirmAuthorizationModule().module,
            StartAuthorizationModule().module,
            AfterStartModule().module,
            NewAccountInfoModule().module,
            ConfigureAccountModule().module,
        )
    }
}