package io.timemates.common.mvi

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow

/**
 * StateMachine is an interface that represents a Model-View-Intent (MVI) architecture
 * and provides a way to manage the state of the UI,
 * handle events, and produce effects.
 *
 * @param TState The type representing the UI state.
 * @param TEvent The type representing events from the UI.
 * @param TEffect The type representing effects to UI.
 */
public interface StateMachine<TState : UiState, TEvent : UiEvent, TEffect : UiEffect> {
    /**
     * Represents the current state of the UI.
     */
    public val state: Flow<TState>

    /**
     * Represents the channel for emitting UI effects.
     */
    public val effects: ReceiveChannel<TEffect>

    /**
     * Dispatches an event to the state machine for processing.
     *
     * @param event The event to be processed.
     */
    public fun dispatchEvent(event: TEvent)
}
