package io.timemates.app.timers.ui.settings.mvi

import androidx.compose.runtime.Immutable
import io.timemates.app.foundation.mvi.AbstractStateMachine
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import io.timemates.app.foundation.mvi.UiState
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.Effect
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.Event
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.State

class TimerSettingsStateMachine(
    reducer: TimerSettingsReducer,
    middleware: TimerSettingsMiddleware,
) : AbstractStateMachine<State, Event, Effect>(
    reducer = reducer,
    middlewares = listOf(middleware),
) {
    override fun initDefaultState(): State {
        return State()
    }

    @Immutable
    data class State(
        val name: String = "",
        val description: String = "",
        val workTime: Int = 25,
        val restTime: Int = 5,
        val period: Int = 4,
        val pauseTime: Int = 10,
        val isNameSizeInvalid: Boolean = false,
        val isDescriptionSizeInvalid: Boolean = false,
        val isAdvancedRestSettingsOn: Boolean = false,
        val isPeriodSizeInvalid: Boolean = false,
        val isPauseTimeSizeInvalid: Boolean = false,
        val isPublicManageTimerStateOn: Boolean = false,
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent {
        data class NameIsChanged(val name: String) : Event()

        data class DescriptionIsChanged(val description: String) : Event()

        data class WorkTimeIsChanged(val workTime: Int) : Event()

        data class RestTimeIsChanged(val restTime: Int) : Event()

        data class PeriodIsChanged(val period: Int) : Event()

        data class PauseTimeIsChanged(val pauseTime: Int) : Event()

        object OnDoneClicked : Event()
    }

    sealed class Effect : UiEffect {
        data class Failure(val throwable: Throwable) : Effect()

        object NavigateToTimersScreen : Effect()
    }
}
