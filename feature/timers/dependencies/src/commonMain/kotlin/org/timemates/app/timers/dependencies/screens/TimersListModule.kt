package org.timemates.app.timers.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton
import org.timemates.app.timers.dependencies.TimersDataModule
import org.timemates.app.timers.ui.timers_list.mvi.TimersListMiddleware
import org.timemates.app.timers.ui.timers_list.mvi.TimersListReducer
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent
import org.timemates.app.users.repositories.TimersRepository
import org.timemates.app.users.usecases.GetUserTimersUseCase

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
        componentContext: ComponentContext,
        getUserTimersUseCase: GetUserTimersUseCase,
        timersListMiddleware: TimersListMiddleware,
    ): TimersListScreenComponent {
        return TimersListScreenComponent(
            componentContext = componentContext,
            reducer = TimersListReducer(
                getUserTimersUseCase = getUserTimersUseCase,
            ),
            middleware = timersListMiddleware,
        )
    }
}