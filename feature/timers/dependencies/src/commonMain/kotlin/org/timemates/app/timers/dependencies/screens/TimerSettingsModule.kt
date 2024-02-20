package org.timemates.app.timers.dependencies.screens

import org.timemates.app.timers.dependencies.TimersDataModule
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsMiddleware
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsReducer
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine
import org.timemates.app.users.repositories.TimersRepository
import org.timemates.app.users.usecases.TimerSettingsUseCase
import org.timemates.app.users.validation.TimerDescriptionValidator
import org.timemates.app.users.validation.TimerNameValidator
import io.timemates.sdk.timers.types.value.TimerId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module(includes = [TimersDataModule::class])
class TimerSettingsModule {

    @Singleton
    fun timerSettingsMiddleware(): TimerSettingsMiddleware = TimerSettingsMiddleware()

    @Factory
    fun timerSettingsUseCase(
        timersRepository: TimersRepository,
    ): TimerSettingsUseCase = TimerSettingsUseCase(timersRepository)

    @Singleton
    fun timerNameValidator(): TimerNameValidator = TimerNameValidator()

    @Singleton
    fun timerDescriptionValidator(): TimerDescriptionValidator = TimerDescriptionValidator()

    @Factory
    fun stateMachine(
        timerId: TimerId,
        timerSettingsUseCase: TimerSettingsUseCase,
        timerNameValidator: TimerNameValidator,
        timerDescriptionValidator: TimerDescriptionValidator,
        middleware: TimerSettingsMiddleware,
    ): TimerSettingsStateMachine {
        return TimerSettingsStateMachine(
            reducer = TimerSettingsReducer(
                timerId = timerId,
                timerSettingsUseCase = timerSettingsUseCase,
                timerNameValidator = timerNameValidator,
                timerDescriptionValidator = timerDescriptionValidator,
            ),
            middleware = middleware,
        )
    }
}
