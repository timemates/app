package org.timemates.app.timers.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import io.timemates.sdk.timers.types.value.TimerId
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton
import org.timemates.app.timers.dependencies.TimersDataModule
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsMiddleware
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsReducer
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent
import org.timemates.app.users.repositories.TimersRepository
import org.timemates.app.users.usecases.TimerSettingsUseCase
import org.timemates.app.users.validation.TimerDescriptionValidator
import org.timemates.app.users.validation.TimerNameValidator

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
        componentContext: ComponentContext,
        timerId: TimerId,
        timerSettingsUseCase: TimerSettingsUseCase,
        timerNameValidator: TimerNameValidator,
        timerDescriptionValidator: TimerDescriptionValidator,
        middleware: TimerSettingsMiddleware,
    ): TimerSettingsScreenComponent {
        return TimerSettingsScreenComponent(
            componentContext = componentContext,
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
