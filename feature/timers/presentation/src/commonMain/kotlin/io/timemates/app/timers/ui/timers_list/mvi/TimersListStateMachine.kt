package io.timemates.app.timers.ui.timers_list.mvi

import androidx.compose.runtime.Immutable
import io.timemates.app.foundation.mvi.StateMachine
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import io.timemates.app.foundation.mvi.UiState
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.Effect
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.Event
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.State
import io.timemates.sdk.timers.types.Timer

class TimersListStateMachine(
    reducer: TimersListReducer,
    middleware: TimersListMiddleware,
) : StateMachine<State, Event, Effect>(
    reducer = reducer,
    middlewares = listOf(middleware),
) {
    override fun initDefaultState(): State {
        return State()
    }

    @Immutable
    data class State(
        val timersList: List<Timer> = emptyList(),
        val hasMoreItems: Boolean = true,
        val isLoading: Boolean = false,
        val isError: Boolean = false,
    ): UiState

    sealed class Event : UiEvent {
        object Load : Event()
    }

    sealed class Effect : UiEffect {
        data class Failure(val throwable: Throwable) : Effect()

        data class LoadTimers(val timersList: List<Timer>) : Effect()

        object NoMoreTimers : Effect()
    }
}
