package io.timemates.common.mvi

/**
 * A reducer is responsible for updating the state based on events and triggering effects.
 *
 * @param TState The type representing the UI state.
 * @param TEvent The type representing events from the UI.
 * @param TEffect The type representing effects to the UI.
 */
public interface Reducer<TState, TEvent, TEffect> {

    /**
     * Reduces the current state based on the given event and triggers effects, if necessary.
     *
     * @param state The current state of the UI.
     * @param event The event to be processed.
     * @param sendEffect The function to send effects to the UI.
     *                   Call `sendEffect(effect)` to send an effect to be handled by the UI.
     *                   The effect will be delivered asynchronously.
     *                   Note: Ensure proper handling and validation of effects to avoid issues like
     *                   infinite loops or unexpected behaviors.
     * @return The new state after processing the event.
     */
    public fun reduce(
        state: TState,
        event: TEvent,
        sendEffect: (TEffect) -> Unit,
    ): TState
}
