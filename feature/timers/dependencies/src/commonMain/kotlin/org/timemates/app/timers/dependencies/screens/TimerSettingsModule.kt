package org.timemates.app.timers.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.timers.dependencies.TimersDataModule
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent
import org.timemates.app.users.repositories.TimersRepository
import org.timemates.app.users.usecases.TimerSettingsUseCase
import org.timemates.sdk.timers.types.value.TimerId

@Module(includes = [TimersDataModule::class])
class TimerSettingsModule {

    @Factory
    fun timerSettingsUseCase(
        timersRepository: TimersRepository,
    ): TimerSettingsUseCase = TimerSettingsUseCase(timersRepository)

    @Factory
    fun stateMachine(
        componentContext: ComponentContext,
        timerId: TimerId,
        timerSettingsUseCase: TimerSettingsUseCase,
    ): TimerSettingsScreenComponent {
        return TimerSettingsScreenComponent(
            componentContext = componentContext,
            timerId = timerId,
            timerSettingsUseCase = timerSettingsUseCase,
        )
    }
}
