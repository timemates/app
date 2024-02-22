package org.timemates.app.timers.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton
import org.timemates.app.timers.dependencies.TimersDataModule
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationMiddleware
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationReducer
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationScreenComponent
import org.timemates.app.users.repositories.TimersRepository
import org.timemates.app.users.usecases.TimerCreationUseCase
import org.timemates.app.users.validation.TimerDescriptionValidator
import org.timemates.app.users.validation.TimerNameValidator

@Module(includes = [TimersDataModule::class])
class TimerCreationModule {

    @Singleton
    fun timerCreationMiddleware(): TimerCreationMiddleware = TimerCreationMiddleware()

    @Factory
    fun timerCreationUseCase(
        timersRepository: TimersRepository,
    ): TimerCreationUseCase = TimerCreationUseCase(timersRepository)

    @Singleton
    fun timerNameValidator(): TimerNameValidator = TimerNameValidator()

    @Singleton
    fun timerDescriptionValidator(): TimerDescriptionValidator = TimerDescriptionValidator()

    @Factory
    fun stateMachine(
        componentContext: ComponentContext,
        timerCreationUseCase: TimerCreationUseCase,
        timerNameValidator: TimerNameValidator,
        timerDescriptionValidator: TimerDescriptionValidator,
        middleware: TimerCreationMiddleware,
    ): TimerCreationScreenComponent {
        return TimerCreationScreenComponent(
            componentContext = componentContext,
            reducer = TimerCreationReducer(
                timerCreationUseCase = timerCreationUseCase,
                timerNameValidator = timerNameValidator,
                timerDescriptionValidator = timerDescriptionValidator,
            ),
            middleware = middleware,
        )
    }
}