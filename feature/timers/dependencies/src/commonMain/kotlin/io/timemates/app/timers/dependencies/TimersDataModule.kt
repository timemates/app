package io.timemates.app.timers.dependencies

import io.timemates.app.users.repositories.TimersRepository
import io.timemates.sdk.common.engine.TimeMatesRequestsEngine
import io.timemates.sdk.common.providers.AccessHashProvider
import io.timemates.sdk.timers.TimersApi
import io.timemates.sdk.timers.sessions.TimersSessionsApi
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

    // TODO remove when `timersSessionsApi` is added to `TimersApi`
    @Factory
    fun timersSessionsApi(
        grpcEngine: TimeMatesRequestsEngine,
        tokenProvider: AccessHashProvider,
    ): TimersSessionsApi = TimersSessionsApi(grpcEngine, tokenProvider)

    @Factory
    fun timersRepository(
        timersApi: TimersApi,
        timersSessionsApi: TimersSessionsApi,
    ): TimersRepository = TimersRepositoryImpl (
        timersApi, timersApi.members.invites, timersSessionsApi, timersApi.members,
    )
}