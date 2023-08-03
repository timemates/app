package io.timemates.app.timers.ui.create_timer.mvi

import androidx.compose.runtime.Immutable
import io.timemates.app.foundation.mvi.AbstractStateMachine
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import io.timemates.app.foundation.mvi.UiState
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.app.timers.ui.create_timer.mvi.CreateTimerStateMachine.State
import io.timemates.app.timers.ui.create_timer.mvi.CreateTimerStateMachine.Event
import io.timemates.app.timers.ui.create_timer.mvi.CreateTimerStateMachine.Effect

class CreateTimerStateMachine(
    reducer: CreateTimerReducer,
    middleware: CreateTimerMiddleware,
): AbstractStateMachine<State, Event, Effect>(reducer, middlewares = listOf(middleware)) {
    override fun initDefaultState(): State = State()

    @Immutable
    data class State(
        val name: String = "",
        val description: String = "",
        val workTime: String = "",
        val restTime: String = "",
        // val isEnabledBigRestTime: Boolean = false,
        val isEnabledTimerState: Boolean = false,
        val isLoading: Boolean = false
    ) : UiState

    sealed class Event : UiEvent {
        data class NameChange(val name: String) : Event()

        data class DescriptionChange(val description: String) : Event()

        data class WorkTimeChange(val workTime: String) : Event()

        data class RestTimeChange(val restTime: String) : Event()

        object OnCreateClick : Event()
    }

    sealed class Effect : UiEffect {
        data class Failure(val throwable: Throwable) : Effect()

        data class NavigateToTimers(
            val verificationHash: VerificationHash,
        ) : Effect()
    }
}