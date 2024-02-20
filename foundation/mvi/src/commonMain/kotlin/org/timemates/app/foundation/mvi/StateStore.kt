package org.timemates.app.foundation.mvi

import kotlinx.coroutines.flow.StateFlow

/**
 * An interface representing a state store.
 *
 * @param TState The type of the state.
 */
public interface StateStore<TState> {
    /**
     * The state flow representing the current state.
     */
    public val state: StateFlow<TState>
}
