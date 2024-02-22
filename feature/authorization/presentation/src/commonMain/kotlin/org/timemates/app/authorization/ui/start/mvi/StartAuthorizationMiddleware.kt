package org.timemates.app.authorization.ui.start.mvi

import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationComponent.Effect
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationComponent.State
import org.timemates.app.foundation.mvi.Middleware

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
     */
    override fun onEffect(effect: Effect, state: State): State {
        return when (effect) {
            is Effect.Failure, Effect.TooManyAttempts, is Effect.NavigateToConfirmation ->
                state.copy(isLoading = false)
        }
    }
}
