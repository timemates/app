package org.timemates.app.timers.ui.settings.mvi

import org.timemates.app.foundation.mvi.Middleware
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.Effect
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.State

class TimerSettingsMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, state: State): State {
        return when (effect) {
            is Effect.Failure ->
                state.copy(isLoading = false)

            else -> state
        }
    }
}