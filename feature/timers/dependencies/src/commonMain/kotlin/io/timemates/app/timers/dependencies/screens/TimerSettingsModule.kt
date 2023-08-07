package io.timemates.app.timers.dependencies.screens

import io.timemates.app.timers.dependencies.TimersDataModule
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsMiddleware
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsReducer
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine
import io.timemates.app.users.repositories.TimersRepository
import io.timemates.app.users.usecases.TimerSettingsUseCase
import io.timemates.app.users.validation.TimerDescriptionValidator
import io.timemates.app.users.validation.TimerNameValidator
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
                coroutineScope = CoroutineScope(Dispatchers.IO),
            ),
            middleware = middleware,
        )
    }
}
