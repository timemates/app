package org.timemates.app.foundation.mvi

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.StateFlow

public interface MVI<TState : UiState, TEvent : UiEvent, TEffect : UiEffect> {
    /**
     * Represents the channel for emitting UI effects.
     */
    public val effects: ReceiveChannel<TEffect>

    /**
     * Represents the current state of the UI.
     */
    public val state: StateFlow<TState>

    /**
     * Processes an event from UI.
     *
     * @param event The event to be processed.
     */
    public fun dispatchEvent(event: TEvent)
}