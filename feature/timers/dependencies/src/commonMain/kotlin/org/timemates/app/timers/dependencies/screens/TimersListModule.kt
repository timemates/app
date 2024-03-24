package org.timemates.app.timers.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.timers.dependencies.TimersDataModule
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent
import org.timemates.app.users.repositories.TimersRepository
import org.timemates.app.users.usecases.GetUserTimersUseCase

@Module(includes = [TimersDataModule::class])
class TimersListModule {

    @Factory
    fun getUserTimersUseCase(
        timersRepository: TimersRepository,
    ): GetUserTimersUseCase = GetUserTimersUseCase(timersRepository)

    @Factory
    fun stateMachine(
        componentContext: ComponentContext,
        getUserTimersUseCase: GetUserTimersUseCase,
        timersRepository: TimersRepository,
    ): TimersListScreenComponent {
        return TimersListScreenComponent(
            componentContext = componentContext,
            getUserTimersUseCase = getUserTimersUseCase,
            timersRepository = timersRepository,
        )
    }
}