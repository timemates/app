package io.timemates.app

import android.app.Application
import android.database.sqlite.SQLiteCursorDriver
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.timemates.android.grpc.AndroidGrpcEngineBuilder
import io.timemates.api.grpc.GrpcTimeMatesRequestsEngine
import io.timemates.app.authorization.dependencies.AuthorizationDataModule
import io.timemates.app.authorization.dependencies.screens.ConfirmAuthorizationModule
import io.timemates.app.authorization.dependencies.screens.StartAuthorizationModule
import io.timemates.data.database.TimeMatesAuthorizations
import io.timemates.sdk.common.engine.TimeMatesRequestsEngine
import org.koin.core.context.startKoin
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.koin.ksp.generated.module

class TimeMatesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            val platformModule = module {
                single<TimeMatesRequestsEngine> {
                    GrpcTimeMatesRequestsEngine(
                        grpcEngineBuilder = AndroidGrpcEngineBuilder(applicationContext)
                    )
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
            )
        }
    }
}