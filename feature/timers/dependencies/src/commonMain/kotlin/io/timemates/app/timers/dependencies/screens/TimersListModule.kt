package io.timemates.app.timers.dependencies.screens

import io.timemates.app.timers.dependencies.TimersDataModule
import io.timemates.app.timers.ui.timers_list.mvi.TimersListMiddleware
import io.timemates.app.timers.ui.timers_list.mvi.TimersListReducer
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine
import io.timemates.app.users.repositories.TimersRepository
import io.timemates.app.users.usecases.GetUserTimersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module(includes = [TimersDataModule::class])
class TimersListModule {

    @Singleton
    fun timersListMiddleware(): TimersListMiddleware = TimersListMiddleware()

    @Factory
    fun getUserTimersUseCase(
        timersRepository: TimersRepository,
    ): GetUserTimersUseCase = GetUserTimersUseCase(timersRepository)

    @Factory
    fun stateMachine(
        getUserTimersUseCase: GetUserTimersUseCase,
        timersListMiddleware: TimersListMiddleware,
    ): TimersListStateMachine {
        return TimersListStateMachine(
            reducer = TimersListReducer(
                getUserTimersUseCase = getUserTimersUseCase,
                coroutineScope = CoroutineScope(Dispatchers.IO),
            ),
            middleware = timersListMiddleware,
        )
    }
}