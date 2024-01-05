package io.timemates.app.timers.dependencies.screens

import io.timemates.app.timers.dependencies.TimersDataModule
import io.timemates.app.timers.ui.timer_creation.mvi.TimerCreationMiddleware
import io.timemates.app.timers.ui.timer_creation.mvi.TimerCreationReducer
import io.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine
import io.timemates.app.users.repositories.TimersRepository
import io.timemates.app.users.usecases.TimerCreationUseCase
import io.timemates.app.users.validation.TimerDescriptionValidator
import io.timemates.app.users.validation.TimerNameValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

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
        timerCreationUseCase: TimerCreationUseCase,
        timerNameValidator: TimerNameValidator,
        timerDescriptionValidator: TimerDescriptionValidator,
        middleware: TimerCreationMiddleware,
    ): TimerCreationStateMachine {
        return TimerCreationStateMachine(
            reducer = TimerCreationReducer(
                timerCreationUseCase = timerCreationUseCase,
                timerNameValidator = timerNameValidator,
                timerDescriptionValidator = timerDescriptionValidator,
            ),
            middleware = middleware,
        )
    }
}