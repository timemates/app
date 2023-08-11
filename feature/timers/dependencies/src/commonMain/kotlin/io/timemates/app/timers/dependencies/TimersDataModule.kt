package io.timemates.app.timers.dependencies

import io.timemates.app.users.repositories.TimersRepository
import io.timemates.sdk.common.engine.TimeMatesRequestsEngine
import io.timemates.sdk.common.providers.AccessHashProvider
import io.timemates.sdk.timers.TimersApi
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import io.timemates.app.timers.data.TimersRepository as TimersRepositoryImpl

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
    ): TimersRepository = TimersRepositoryImpl (
        timersApi, timersApi.members.invites, timersApi.sessions, timersApi.members,
    )
}