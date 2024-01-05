package io.timemates.app.authorization.ui.start.mvi

import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.State
import io.timemates.app.foundation.mvi.Middleware
import io.timemates.app.foundation.mvi.StateStore

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
     */
    override fun onEffect(effect: Effect, store: StateStore<State>): State {
        return when (effect) {
            is Effect.Failure, Effect.TooManyAttempts, is Effect.NavigateToConfirmation ->
                store.state.value.copy(isLoading = false)
        }
    }
}
