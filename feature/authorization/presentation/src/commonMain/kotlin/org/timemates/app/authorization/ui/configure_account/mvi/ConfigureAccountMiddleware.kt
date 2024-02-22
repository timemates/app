package org.timemates.app.authorization.ui.configure_account.mvi

import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.Effect
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.State
import org.timemates.app.foundation.mvi.Middleware

class ConfigureAccountMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, state: State): State {
        return when (effect) {
            is Effect.Failure ->
                state.copy(isLoading = false)

            else -> state
        }
    }
}
