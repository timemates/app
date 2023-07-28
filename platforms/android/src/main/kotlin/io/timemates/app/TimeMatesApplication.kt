package io.timemates.app

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.timemates.android.grpc.AndroidGrpcEngineBuilder
import io.timemates.api.grpc.GrpcTimeMatesRequestsEngine
import io.timemates.app.authorization.dependencies.AuthorizationDataModule
import io.timemates.app.authorization.dependencies.screens.AfterStartModule
import io.timemates.app.authorization.dependencies.screens.ConfirmAuthorizationModule
import io.timemates.app.authorization.dependencies.screens.StartAuthorizationModule
import io.timemates.app.core.handler.OnAuthorizationFailedHandler
import io.timemates.data.database.TimeMatesAuthorizations
import io.timemates.sdk.common.engine.TimeMatesRequestsEngine
import kotlinx.coroutines.channels.Channel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.koin.ksp.generated.module

class TimeMatesApplication : Application() {

    companion object {
        internal val AUTH_FAILED_CHANNEL: Channel<Unit> = Channel()
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            val platformModule = module {
                single<TimeMatesRequestsEngine> {
                    GrpcTimeMatesRequestsEngine(
                        grpcEngineBuilder = AndroidGrpcEngineBuilder(applicationContext)
                    )
                }

                single<OnAuthorizationFailedHandler> {
                    OnAuthorizationFailedHandler { exception ->
                        exception.printStackTrace()
                        AUTH_FAILED_CHANNEL.trySend(Unit)
                    }
                }

                single<SqlDriver>(qualifier = qualifier("authorization")) {
                    AndroidSqliteDriver(TimeMatesAuthorizations.Schema, applicationContext)
                }
            }

            modules(
                platformModule,
                AuthorizationDataModule().module,
                ConfirmAuthorizationModule().module,
                StartAuthorizationModule().module,
                AfterStartModule().module,
            )
        }
    }
}