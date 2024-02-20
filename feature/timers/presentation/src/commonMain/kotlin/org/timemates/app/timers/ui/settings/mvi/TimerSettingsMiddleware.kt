package org.timemates.app.timers.ui.settings.mvi

import org.timemates.app.foundation.mvi.Middleware
import org.timemates.app.foundation.mvi.StateStore
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.Effect
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.State

class TimerSettingsMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, state: State): State {
        return when (effect) {
            is Effect.Failure ->
                state.copy(isLoading = false)

            else -> state
        }
    }
}