package io.timemates.app.authorization.ui.confirmation.mvi

import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.State
import io.timemates.app.foundation.mvi.Middleware
import io.timemates.app.foundation.mvi.StateStore

/**
 * This middleware works with async operation effects and updates the state when necessary.
 * Specifically, when an [Effect.Failure] or [Effect.TooManyAttempts] is received, it removes
 * the loading state from the UI.
 */
class ConfirmAuthorizationMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, store: StateStore<State>): State {
        return when (effect) {
            is Effect.Failure, Effect.TooManyAttempts, Effect.AttemptIsFailed ->
                store.state.value.copy(isLoading = false)

            else -> store.state.value
        }
    }
}
