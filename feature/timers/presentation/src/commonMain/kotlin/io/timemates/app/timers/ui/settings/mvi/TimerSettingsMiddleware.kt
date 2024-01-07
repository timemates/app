package io.timemates.app.timers.ui.settings.mvi

import io.timemates.app.foundation.mvi.Middleware
import io.timemates.app.foundation.mvi.StateStore
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.Effect
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.State

class TimerSettingsMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, state: State): State {
        return when (effect) {
            is Effect.Failure ->
                state.copy(isLoading = false)

            else -> state
        }
    }
}