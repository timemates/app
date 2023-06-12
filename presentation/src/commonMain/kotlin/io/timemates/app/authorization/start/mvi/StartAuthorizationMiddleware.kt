package io.timemates.app.authorization.start.mvi

import io.timemates.app.authorization.start.mvi.StartAuthorizationStateMachine.*
import io.timemates.common.mvi.Middleware
import io.timemates.common.mvi.StateStore

/**
 * A middleware responsible for handling effects in the Start Authorization screen.
 */
class StartAuthorizationMiddleware : Middleware<State, Effect> {

    /**
     * Handles the specified effect and updates the state if necessary.
     *
     * This function is called when an effect is dispatched within the Start Authorization feature.
     * It receives the effect, the current state store, and a function to update the state.
     * The purpose of this function is to react to specific effects and modify the state accordingly.
     * In this case, when an [Effect.Failure] or [Effect.TooManyAttempts] is received,
     * it sets the loading state in the UI to false by updating the state.
     *
     * @param effect The effect to be handled.
     * @param store The state store containing the current state.
     * @param setState A function to update the state.
     */
    override fun onEffect(effect: Effect, store: StateStore<State>, setState: (State) -> Unit) {
        when (effect) {
            is Effect.Failure, Effect.TooManyAttempts ->
                setState(store.state.value.copy(isLoading = false))
            else -> {}
        }
    }
}
