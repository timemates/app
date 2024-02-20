package org.timemates.app.authorization.ui.configure_account.mvi

import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.Effect
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.State
import org.timemates.app.foundation.mvi.Middleware
import org.timemates.app.foundation.mvi.StateStore

class ConfigureAccountMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, state: State): State {
        return when (effect) {
            is Effect.Failure ->
                state.copy(isLoading = false)

            else -> state
        }
    }
}
