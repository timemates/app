package io.timemates.app.authorization.confirmation.mvi

import io.timemates.app.authorization.confirmation.mvi.ConfirmAuthorizationStateMachine.*
import io.timemates.common.mvi.Middleware
import io.timemates.common.mvi.StateStore
import org.koin.core.annotation.Singleton

/**
 * This middleware works with async operation effects and updates the state when necessary.
 * Specifically, when an [Effect.Failure] or [Effect.TooManyAttempts] is received, it removes
 * the loading state from the UI.
 */
@Singleton
class ConfirmAuthorizationMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, store: StateStore<State>, setState: (State) -> Unit) {
        when (effect) {
            is Effect.Failure, Effect.TooManyAttempts ->
                setState(store.state.value.copy(isLoading = false))
            else -> {}
        }
    }
}
