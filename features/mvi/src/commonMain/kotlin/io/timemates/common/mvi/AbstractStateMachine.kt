package io.timemates.common.mvi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Abstract class that should be typically used for implementing [StateMachine].
 */
public abstract class AbstractStateMachine<TState : UiState, TEvent : UiEvent, TEffect : UiEffect>(
    private val reducer: Reducer<TState, TEvent, TEffect>,
    private val middlewares: List<Middleware<TState, TEffect>>,
) : StateMachine<TState, TEvent, TEffect> {

    protected abstract fun initDefaultState(): TState

    private val _state: MutableStateFlow<TState> by lazy { MutableStateFlow(initDefaultState()) }
    private val _effects: Channel<TEffect> = Channel(Channel.UNLIMITED)

    /**
     * Represents the current state of the UI.
     */
    public final override val state: StateFlow<TState> by lazy { _state.asStateFlow() }

    /**
     * Represents the channel for emitting UI effects.
     */
    public final override val effects: ReceiveChannel<TEffect> = _effects

    /**
     * Processes an event from UI.
     *
     * @param event The event to be processed.
     */
    public final override fun dispatchEvent(event: TEvent) {
        val sendEffect: (TEffect) -> Unit = { effect ->
            middlewares.forEach { middleware -> middleware.onEffect(effect, this, ::setState) }
            _effects.trySend(effect)
        }

        setState(reducer.reduce(state.value, event, sendEffect))
    }

    private fun setState(state: TState) {
        _state.tryEmit(state)
    }
}
