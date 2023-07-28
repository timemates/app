package io.timemates.app.authorization.ui.configure_account.mvi

import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.Effect
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.State
import io.timemates.app.foundation.mvi.Middleware
import io.timemates.app.foundation.mvi.StateStore

class ConfigureAccountMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, store: StateStore<State>): State {
        return when (effect) {
            is Effect.Failure ->
                store.state.value.copy(isLoading = false)

            else -> store.state.value
        }
    }
}
