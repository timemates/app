package org.timemates.app.timers.dependencies

import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton
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
    fun timersRepository(
        timersApi: TimersApi,
    ): TimersRepository = TimersRepositoryImpl(
        timersApi, timersApi.members.invites, timersApi.sessions, timersApi.members,
    )
}