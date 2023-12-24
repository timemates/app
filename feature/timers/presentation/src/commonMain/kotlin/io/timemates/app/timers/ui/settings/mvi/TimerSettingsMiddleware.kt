package io.timemates.app.timers.ui.settings.mvi

import io.timemates.app.foundation.mvi.Middleware
import io.timemates.app.foundation.mvi.StateStore
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.Effect
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.State

class TimerSettingsMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, store: StateStore<State>): State {
        return when (effect) {
            is Effect.Failure ->
                store.state.value.copy(isLoading = false)

            else -> store.state.value
        }
    }
}