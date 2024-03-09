package org.timemates.app.timers.ui.timers_list.mvi

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.timemates.app.core.types.serializable.SerializableTimer
import org.timemates.sdk.timers.types.Timer
import org.timemates.app.foundation.mvi.MVIComponent
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState
import org.timemates.app.foundation.mvi.mviComponent
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.Effect
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.Event
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.State

class TimersListScreenComponent(
    componentContext: ComponentContext,
    reducer: TimersListReducer,
    middleware: TimersListMiddleware,
) : MVIComponent<State, Event, Effect> by mviComponent(
    componentName = "TimersListScreen",
    componentContext = componentContext,
    initState = State(),
    reducer = reducer,
    middlewares = listOf(middleware),
) {
    @Serializable
    @Immutable
    data class State(
        val timersList: List<SerializableTimer> = emptyList(),
        val hasMoreItems: Boolean = true,
        val isLoading: Boolean = false,
        val isError: Boolean = false,
    ): UiState

    sealed class Event : UiEvent {
        data object Load : Event()
    }

    sealed class Effect : UiEffect {
        data class Failure(val throwable: Throwable) : Effect()

        data class LoadTimers(val timersList: List<Timer>) : Effect()

        data object NoMoreTimers : Effect()
    }
}
