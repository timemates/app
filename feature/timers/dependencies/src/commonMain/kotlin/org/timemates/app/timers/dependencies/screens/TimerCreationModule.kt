package org.timemates.app.timers.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.timers.dependencies.TimersDataModule
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationScreenComponent
import org.timemates.app.users.repositories.TimersRepository
import org.timemates.app.users.usecases.TimerCreationUseCase

@Module(includes = [TimersDataModule::class])
class TimerCreationModule {

    @Factory
    fun timerCreationUseCase(
        timersRepository: TimersRepository,
    ): TimerCreationUseCase = TimerCreationUseCase(timersRepository)

    @Factory
    fun stateMachine(
        componentContext: ComponentContext,
        timerCreationUseCase: TimerCreationUseCase,
    ): TimerCreationScreenComponent {
        return TimerCreationScreenComponent(
            componentContext = componentContext,
            timerCreationUseCase = timerCreationUseCase,
        )
    }
}