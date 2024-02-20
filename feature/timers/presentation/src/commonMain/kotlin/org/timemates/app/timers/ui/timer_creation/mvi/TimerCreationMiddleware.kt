package org.timemates.app.timers.ui.timer_creation.mvi

import org.timemates.app.foundation.mvi.Middleware
import org.timemates.app.foundation.mvi.StateStore
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine.Effect
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine.State

class TimerCreationMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, state: State): State {
        return when (effect) {
            is Effect.Failure ->
                state.copy(isLoading = false)

            else -> state
        }
    }
}