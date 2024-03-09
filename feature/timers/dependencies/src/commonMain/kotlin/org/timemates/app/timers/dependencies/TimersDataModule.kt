package org.timemates.app.timers.dependencies

import org.timemates.app.users.repositories.TimersRepository
import org.timemates.sdk.common.engine.TimeMatesRequestsEngine
import org.timemates.sdk.common.providers.AccessHashProvider
import org.timemates.sdk.timers.TimersApi
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.timers.data.TimersRepository as TimersRepositoryImpl

@Module
class TimersDataModule {
    @Factory
    fun timersApi(
        grpcEngine: TimeMatesRequestsEngine,
        tokenProvider: AccessHashProvider,
    ): TimersApi = TimersApi(grpcEngine, tokenProvider)

    @Factory
    fun timersRepository(
        timersApi: TimersApi,
    ): TimersRepository = TimersRepositoryImpl(
        timersApi, timersApi.members.invites, timersApi.sessions, timersApi.members,
    )
}