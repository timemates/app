package org.timemates.app.foundation.mvi

import org.timemates.androidx.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

public abstract class StateMachine<TState : UiState, TEvent : UiEvent, TEffect : UiEffect>(
    protected val initState: TState,
    private val reducer: Reducer<TState, TEvent, TEffect>,
    private val middlewares: List<Middleware<TState, TEffect>> = emptyList(),
) : ViewModel(), StateStore<TState> {
    private val _state: MutableStateFlow<TState> by lazy { MutableStateFlow(initState) }
    private val _effects: Channel<TEffect> = Channel(Channel.UNLIMITED)

    /**
     * Represents the current state of the UI.
     */
    public final override val state: StateFlow<TState> by ::_state

    /**
     * Represents the channel for emitting UI effects.
     */
    public val effects: ReceiveChannel<TEffect> by ::_effects

    private val sendEffect: (TEffect) -> Unit = { effect ->
        middlewares.forEach { middleware -> updateState { middleware.onEffect(effect, it) } }
        _effects.trySend(effect)
    }

    private val reducerScope = ReducerScope(sendEffect, coroutineScope)

    /**
     * Processes an event from UI.
     *
     * @param event The event to be processed.
     */
    public fun dispatchEvent(event: TEvent) {
        with(reducer) {
            updateState { reducerScope.reduce(it, event) }
        }
    }

    private fun updateState(action: (TState) -> TState) {
        _state.update { action(it) }
    }
}
