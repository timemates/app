package org.timemates.app.timers.ui.settings.mvi

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.common.types.value.Count
import org.timemates.app.foundation.mvi.MVIComponent
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState
import org.timemates.app.foundation.mvi.mviComponent
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.Effect
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.Event
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.State
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class TimerSettingsScreenComponent(
    componentContext: ComponentContext,
    reducer: TimerSettingsReducer,
    middleware: TimerSettingsMiddleware,
) : MVIComponent<State, Event, Effect> by mviComponent(
    componentContext = componentContext,
    componentName = "TimerSettingsScreen",
    initState = State(),
    reducer = reducer,
    middlewares = listOf(middleware),
) {
    @Immutable
    data class State(
        val name: String = "",
        val description: String = "",
        val workTime: Duration = 25.minutes,
        val restTime: Duration = 5.minutes,
        val bigRestEnabled: Boolean = true,
        val bigRestPer: Count = Count.createOrThrow(4),
        val bigRestTime: Duration = 10.minutes,
        val isEveryoneCanPause: Boolean = false,
        val isConfirmationRequired: Boolean = true,
        val isNameSizeInvalid: Boolean = false,
        val isDescriptionSizeInvalid: Boolean = false,
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent {
        data class NameIsChanged(val name: String) : Event()

        data class DescriptionIsChanged(val description: String) : Event()

        data class WorkTimeIsChanged(val workTime: Duration) : Event()

        data class RestTimeIsChanged(val restTime: Duration) : Event()

        data class BigRestModeIsChanged(val bigRestEnabled: Boolean) : Event()

        data class BigRestPerIsChanged(val bigRestPer: Count) : Event()

        data class BigRestTimeIsChanged(val bigRestTime: Duration) : Event()

        data class TimerPauseControlAccessIsChanged(val isEveryoneCanPause: Boolean) : Event()

        data class ConfirmationRequirementChanged(val isConfirmationRequired: Boolean) : Event()

        data object OnDoneClicked : Event()
    }

    sealed class Effect : UiEffect {
        data class Failure(val throwable: Throwable) : Effect()

        data object Success : Effect()

        data object NavigateToTimersScreen : Effect()
    }
}
