package org.timemates.app.foundation.mvi

import kotlinx.coroutines.CoroutineScope

/**
 * A reducer is responsible for updating the state based on events and triggering effects.
 *
 * @param TState The type representing the UI state.
 * @param TEvent The type representing events from the UI.
 * @param TEffect The type representing effects to the UI.
 */
public interface Reducer<TState : UiState, TEvent : UiEvent, TEffect : UiEffect> {

    /**
     * Reduces the current state based on the given event and triggers effects, if necessary.
     *
     * @param state The current state of the UI.
     * @param event The event to be processed.
     * @return The new state after processing the event.
     */
    public fun ReducerScope<TEffect>.reduce(
        state: TState,
        event: TEvent,
    ): TState
}

public fun <TState : UiState, TEvent : UiEvent, TEffect : UiEffect> Reducer<TState, TEvent, TEffect>.reduce(
    state: TState,
    event: TEvent,
    machineScope: CoroutineScope,
    sendEffect: (TEffect) -> Unit,
): TState {
    return ReducerScope(sendEffect, machineScope).reduce(state, event)
}

/**
 * Reducer's Scope with additional functionality and information.
 *
 * @param sendEffect The function to send effects to the UI.
 *                   Call `sendEffect(effect)` to send an effect to be handled by the UI.
 *                   The effect will be delivered asynchronously.
 *                   Note: Ensure proper handling and validation of effects to avoid issues like
 *                   infinite loops or unexpected behaviors.
 * @param machineScope CoroutineScope that is linked to the mvi.
 */
public data class ReducerScope<TEffect : UiEffect>(
    val sendEffect: (TEffect) -> Unit,
    val machineScope: CoroutineScope,
)
