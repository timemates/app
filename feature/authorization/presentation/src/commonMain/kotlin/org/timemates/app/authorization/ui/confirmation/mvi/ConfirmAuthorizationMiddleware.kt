package org.timemates.app.authorization.ui.confirmation.mvi

import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.Effect
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.State
import org.timemates.app.foundation.mvi.Middleware

/**
 * This middleware works with async operation effects and updates the state when necessary.
 * Specifically, when an [Effect.Failure] or [Effect.TooManyAttempts] is received, it removes
 * the loading state from the UI.
 */
class ConfirmAuthorizationMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, state: State): State {
        return when (effect) {
            is Effect.Failure,
            Effect.TooManyAttempts,
            Effect.AttemptIsFailed,
            is Effect.NavigateToCreateAccount,
            is Effect.NavigateToHome,
            -> state.copy(isLoading = false)
        }
    }
}
