package org.timemates.app.timers.dependencies

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Singleton
import org.timemates.app.foundation.time.TimeProvider
import org.timemates.app.timers.data.datasource.DatabaseTimersSource
import org.timemates.app.users.repositories.TimersRepository
import org.timemates.sdk.common.engine.TimeMatesRequestsEngine
import org.timemates.sdk.common.providers.AccessHashProvider
import org.timemates.sdk.timers.TimersApi
import org.timemates.app.timers.data.TimersRepository as TimersRepositoryImpl

@Module
class TimersDataModule {
    @Singleton
    fun timersApi(
        engine: TimeMatesRequestsEngine,
        tokenProvider: AccessHashProvider,
    ): TimersApi = TimersApi(engine, tokenProvider)

    @Singleton
    fun dbTimersSource(@Named("timers") driver: SqlDriver): DatabaseTimersSource {
        return DatabaseTimersSource(driver)
    }

    @Singleton
    fun timersRepository(
        timersApi: TimersApi,
        dbTimersSource: DatabaseTimersSource,
        timeProvider: TimeProvider,
    ): TimersRepository = TimersRepositoryImpl(
        timersApi, timersApi.sessions, dbTimersSource, timeProvider,
    )
}